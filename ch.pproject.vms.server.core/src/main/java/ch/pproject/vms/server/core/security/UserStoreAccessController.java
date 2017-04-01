package ch.pproject.vms.server.core.security;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.IPrincipalProducer;
import org.eclipse.scout.rt.platform.security.SecurityUtility;
import org.eclipse.scout.rt.platform.security.SimplePrincipalProducer;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.Base64Utility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.commons.authentication.IAccessController;
import org.eclipse.scout.rt.server.commons.authentication.ServletFilterHelper;
import org.eclipse.scout.rt.shared.servicetunnel.http.DefaultAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.pproject.vms.server.core.services.common.sql.MySQLSqlService;

public class UserStoreAccessController implements IAccessController {

  private static final Logger LOG = LoggerFactory.getLogger(UserStoreAccessController.class);

  protected UserStoreConfig m_config;

  public UserStoreAccessController init(final UserStoreConfig config) throws ServletException {
    m_config = config;
    return this;
  }

  @Override
  public boolean handle(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    Entry<String, char[]> basicAuth = readCredentials(request);
    String username = basicAuth.getKey();
    char[] passwordPlainText = basicAuth.getValue();

    if (StringUtility.isNullOrEmpty(username) || passwordPlainText == null || passwordPlainText.length == 0) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
      return true;
    }

    if (!username.matches("^[a-z]*$")) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
      return true;
    }
    MySQLSqlService sqlService = BEANS.get(MySQLSqlService.class);
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(sqlService.getJdbcMappingName(), sqlService.getUsername(), sqlService.getPassword());

      PreparedStatement stmt = connection.prepareStatement("" +
          "SELECT " +
          " PASSWORD " +
          "FROM USER " +
          "WHERE USERNAME = ? ");
      stmt.setString(1, username);
      stmt.execute();
      ResultSet resultSet = stmt.getResultSet();

      if (resultSet.next()) {
        String hashAndSalt = resultSet.getString(1);
        if (StringUtility.isNullOrEmpty(hashAndSalt)) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
          return true;
        }
        final String[] tokens = hashAndSalt.split("\\.");
        Assertions.assertEqual(2, tokens.length, "Invalid password entry: salt and password-hash are to be separated with the dot (.).");
        Assertions.assertGreater(tokens[0].length(), 0, "Invalid password entry: 'salt' must not be empty");
        Assertions.assertGreater(tokens[1].length(), 0, "Invalid password entry: 'password-hash' must not be empty");
        byte[] salt = Base64Utility.decode(tokens[0]);
        byte[] hash = Base64Utility.decode(tokens[1]);

        if (Arrays.equals(hash, SecurityUtility.hash(VmsSecurityUtility.toBytes(passwordPlainText), salt))) {
          response.sendError(HttpServletResponse.SC_OK);
          return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return true;
      }
    }
    catch (Exception e) {
      LOG.error("Cannot SELECT user/pass.", e);
      throw new ServletException(e.getMessage(), e);
    }
    finally {
      try {
        if (connection != null) {
          connection.close();
          connection = null;
        }
      }
      catch (SQLException e) {
        LOG.warn("Exception in close connection!", e);
      }
    }
    return false;

  }

  protected Entry<String, char[]> readCredentials(final HttpServletRequest req) {
    final String[] basicCredentials = BEANS.get(ServletFilterHelper.class).parseBasicAuthRequest(req);
    if (basicCredentials == null || basicCredentials.length != 2) {
      return null;
    }
    final String user = basicCredentials[0];
    if (StringUtility.isNullOrEmpty(user)) {
      return null;
    }
    final String password = basicCredentials[1];
    if (StringUtility.isNullOrEmpty(password)) {
      return null;
    }

    return new SimpleEntry<>(user, password.toCharArray());
  }

  @Override
  public void destroy() {
    // NOOP
  }

  @SuppressWarnings("unused")
  private static class PasswordGenerator {

    public static void main(String[] args) {
      char[] password = "".toCharArray();
      byte[] salt = SecurityUtility.createRandomBytes();
      byte[] hash = SecurityUtility.hash(VmsSecurityUtility.toBytes(password), salt);

      System.out.println(String.format("%s.%s", Base64Utility.encode(salt), Base64Utility.encode(hash)));
    }

  }

  public static class UserStoreConfig {

    private boolean m_enabled = DefaultAuthToken.isEnabled();
    private IPrincipalProducer m_principalProducer = BEANS.get(SimplePrincipalProducer.class);

    public boolean isEnabled() {
      return m_enabled;
    }

    public UserStoreConfig withEnabled(final boolean enabled) {
      m_enabled = enabled;
      return this;
    }

    public IPrincipalProducer getPrincipalProducer() {
      return m_principalProducer;
    }

    public UserStoreConfig withPrincipalProducer(final IPrincipalProducer principalProducer) {
      m_principalProducer = principalProducer;
      return this;
    }
  }
}

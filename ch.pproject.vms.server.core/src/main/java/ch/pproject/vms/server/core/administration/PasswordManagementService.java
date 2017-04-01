package ch.pproject.vms.server.core.administration;

import java.util.Arrays;
import java.util.Date;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.security.SecurityUtility;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.Base64Utility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.server.services.common.pwd.AbstractPasswordManagementService;

import ch.pproject.vms.server.core.security.VmsSecurityUtility;

public class PasswordManagementService extends AbstractPasswordManagementService {

  @Override
  public Date getPasswordExpirationDate(String userId) {
    return null; // no expiration
  }

  @Override
  protected void checkAccess(String userId, String password) {
    StringHolder hashAndSaltHolder = new StringHolder();
    SQL.selectInto("" +
        "SELECT " +
        " PASSWORD " +
        "FROM USER " +
        "WHERE USERNAME = :userId " +
        "INTO :hashAndSalt",
        new NVPair("userId", userId),
        new NVPair("hashAndSalt", hashAndSaltHolder));
    String hashAndSalt = hashAndSaltHolder.getValue();
    if (StringUtility.isNullOrEmpty(hashAndSalt)) {
      throw new VetoException();
    }
    final String[] tokens = hashAndSalt.split("\\.");
    Assertions.assertEqual(2, tokens.length, "Invalid password entry: salt and password-hash are to be separated with the dot (.).");
    Assertions.assertGreater(tokens[0].length(), 0, "Invalid password entry: 'salt' must not be empty");
    Assertions.assertGreater(tokens[1].length(), 0, "Invalid password entry: 'password-hash' must not be empty");
    byte[] salt = Base64Utility.decode(tokens[0]);
    byte[] hash = Base64Utility.decode(tokens[1]);

    if (!Arrays.equals(hash, SecurityUtility.hash(VmsSecurityUtility.toBytes(password.toCharArray()), salt))) {
      throw new VetoException();
    }
  }

  @Override
  protected String getUsernameFor(String userId) {
    return userId;
  }

  @Override
  protected int getHistoryIndexFor(String userId, String password) {
    return -1; // no history
  }

  @Override
  protected void resetPasswordInternal(String userId, String newPassword) {
    String password = VmsSecurityUtility.createPasswordHash(newPassword);
    SQL.update("" +
        "UPDATE USER SET " +
        " PASSWORD = :password " +
        "WHERE USERNAME = :userId ",
        new NVPair("password", password),
        new NVPair("userId", userId));
  }

}

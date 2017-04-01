package ch.pproject.vms.ui.html;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.server.commons.authentication.ServletFilterHelper;
import org.eclipse.scout.rt.shared.SharedConfigProperties.BackendUrlProperty;

public class DataSourceCredentialVerifierProxy implements ICredentialVerifier {

  @Override
  public int verify(String username, char[] password) throws IOException {
    URL backendUrl = new URL(CONFIG.getPropertyValue(BackendUrlProperty.class) + "/auth");
    final HttpURLConnection con = (HttpURLConnection) backendUrl.openConnection();
    con.setDefaultUseCaches(false);
    con.setUseCaches(false);
    con.setRequestProperty(ServletFilterHelper.HTTP_HEADER_AUTHORIZATION, BEANS.get(ServletFilterHelper.class).createBasicAuthRequest(username, password));
    switch (con.getResponseCode()) {
      case HttpURLConnection.HTTP_OK:
        return AUTH_OK;
      default:
        return AUTH_FORBIDDEN;
    }
  }

}

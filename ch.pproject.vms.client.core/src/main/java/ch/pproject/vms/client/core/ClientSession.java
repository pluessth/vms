package ch.pproject.vms.client.core;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

import ch.pproject.vms.client.core.ui.desktop.Desktop;

public class ClientSession extends AbstractClientSession {

  public ClientSession() {
    super(true);
  }

  /**
   * @return The {@link IClientSession} which is associated with the current thread, or <code>null</code> if not found.
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  public void execLoadSession() {
    //pre-load all known code types
    CODES.getAllCodeTypes("ch.pproject.vms.shared.core");

    setDesktop(new Desktop());
  }
}

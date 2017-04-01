package ch.pproject.vms.shared.core.administration;

import java.security.BasicPermission;

import ch.pproject.vms.shared.core.security.IVmsPermission;

public abstract class AbstractVmsPermission extends BasicPermission implements IVmsPermission {
  private static final long serialVersionUID = 1L;

  public AbstractVmsPermission(String simpleName) {
    super(simpleName);
  }

}

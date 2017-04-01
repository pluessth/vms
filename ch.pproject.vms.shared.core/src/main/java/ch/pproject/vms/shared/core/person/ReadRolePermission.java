package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadRolePermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public ReadRolePermission() {
    super(ReadRolePermission.class.getSimpleName());
  }
}

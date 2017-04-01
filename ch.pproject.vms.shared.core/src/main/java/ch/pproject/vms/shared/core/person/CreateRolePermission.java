package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreateRolePermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public CreateRolePermission() {
    super(CreateRolePermission.class.getSimpleName());
  }
}

package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class DeleteRolePermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public DeleteRolePermission() {
    super(DeleteRolePermission.class.getSimpleName());
  }
}

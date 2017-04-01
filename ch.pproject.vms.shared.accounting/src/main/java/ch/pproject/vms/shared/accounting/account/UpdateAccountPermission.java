package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class UpdateAccountPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public UpdateAccountPermission() {
    super(UpdateAccountPermission.class.getSimpleName());
  }
}

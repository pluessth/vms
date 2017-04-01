package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class UpdateAccountingYearPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public UpdateAccountingYearPermission() {
    super(UpdateAccountingYearPermission.class.getSimpleName());
  }
}

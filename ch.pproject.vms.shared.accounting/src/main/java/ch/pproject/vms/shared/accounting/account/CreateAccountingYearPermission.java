package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreateAccountingYearPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public CreateAccountingYearPermission() {
    super(CreateAccountingYearPermission.class.getSimpleName());
  }
}

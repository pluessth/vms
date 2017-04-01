package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadAccountingYearPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public ReadAccountingYearPermission() {
    super(ReadAccountingYearPermission.class.getSimpleName());
  }
}

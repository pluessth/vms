package ch.pproject.vms.server.accounting.account;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import ch.pproject.vms.shared.accounting.account.IAccountingYearLookupService;

public class AccountingYearLookupService extends AbstractSqlLookupService<Long> implements IAccountingYearLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return ""
        + "SELECT "
        + " ACCOUNTING_YEAR_NR, "
        + " NAME "
        + "FROM ACCOUNTING_YEAR "
        + "WHERE 1=1 "
        + "<all>AND (IS_CLOSED = :showClosedAccountingYears OR :showClosedAccountingYears = true) </all>"
        + "<key>AND ACCOUNTING_YEAR_NR = :key </key>"
        + "<text>AND NAME LIKE :text AND (IS_CLOSED = :showClosedAccountingYears OR :showClosedAccountingYears = true) </text>";
  }

}

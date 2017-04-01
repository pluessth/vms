package ch.pproject.vms.server.accounting.account;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import ch.pproject.vms.shared.accounting.account.IAccountLookupService;

public class AccountLookupService extends AbstractSqlLookupService<Long> implements IAccountLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT ACCOUNT_NR, "
        + " ACCOUNT_NAME "
        + "FROM ACCOUNT "
        + "WHERE 1=1 "
        + "AND (ACCOUNTING_YEAR_NR = :master OR :master IS NULL)"
        + "<key>AND ACCOUNT_NR = :key </key>"
        + "<text>AND ACCOUNT_NAME LIKE :text </text>";
  }
}

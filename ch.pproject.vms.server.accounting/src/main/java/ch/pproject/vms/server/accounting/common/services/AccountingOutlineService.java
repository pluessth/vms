package ch.pproject.vms.server.accounting.common.services;

import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import ch.pproject.vms.shared.accounting.account.AccountingYearTablePageData;
import ch.pproject.vms.shared.accounting.account.AccountsTablePageData;
import ch.pproject.vms.shared.accounting.posting.PostingSearchFormData;
import ch.pproject.vms.shared.accounting.posting.PostingsPerAccountTablePageData;
import ch.pproject.vms.shared.accounting.posting.PostingsTablePageData;
import ch.pproject.vms.shared.accounting.services.code.PostingStatusCodeType;
import ch.pproject.vms.shared.accounting.services.common.IAccountingOutlineService;

public class AccountingOutlineService implements IAccountingOutlineService {

  public static final String POSTING_INTO_CLAUSE = "" +
      "INTO " +
      " :{page.postingNr}, " +
      " :{page.voucherNo}, " +
      " :{page.postingDate}, " +
      " :{page.accountingYear}, " +
      " :{page.debitAccount}, " +
      " :{page.creditAccount}, " +
      " :{page.amount}, " +
      " :{page.postingText}, " +
      " :{page.person}, " +
      " :{page.activity}, " +
      " :{page.status} ";

  @Override
  public AbstractTablePageData getPostingsTableData(PostingSearchFormData formData) {

    StringBuilder statement = new StringBuilder();
    statement.append("" +
        "SELECT " +
        " POSTING_NR, " +
        " VOUCHER_NO, " +
        " POSTING_DATE, " +
        " P.ACCOUNTING_YEAR_NR, " +
        " DA.ACCOUNT_NAME, " +
        " CA.ACCOUNT_NAME, " +
        " AMOUNT, " +
        " POSTING_TEXT, " +
        " P.PERSON_NR, " +
        " ACTIVITY_NR, " +
        " STATUS_UID " +
        "FROM POSTING P " +
        "LEFT OUTER JOIN ACCOUNT CA ON P.CREDIT_ACCOUNT_NR = CA.ACCOUNT_NR " +
        "LEFT OUTER JOIN ACCOUNT DA ON P.DEBIT_ACCOUNT_NR = DA.ACCOUNT_NR " +
        "LEFT OUTER JOIN PERSON PE ON P.PERSON_NR = PE.PERSON_NR " +
        "WHERE 1=1 ");
    if (formData != null) {
      if (!StringUtility.isNullOrEmpty(formData.getVoucherNo().getValue())) {
        statement.append("AND UPPER(VOUCHER_NO) LIKE UPPER(:voucherNo) ");
      }
      if (formData.getPostingDateFrom().getValue() != null) {
        statement.append("AND POSTING_DATE >= :postingDateFrom ");
      }
      if (formData.getPostingDateTo().getValue() != null) {
        statement.append("AND POSTING_DATE <= :postingDateTo ");
      }
      if (formData.getAccountingYear().getValue() != null) {
        statement.append("AND P.ACCOUNTING_YEAR_NR = :accountingYear ");
      }
      if (formData.getAccount().getValue() != null) {
        String value = formData.getAccountRadioButtonGroup().getValue();
        if ("Credit".equals(value)) {
          statement.append("AND P.CREDIT_ACCOUNT_NR = :account ");
        }
        else if ("Debit".equals(value)) {
          statement.append("AND P.DEBIT_ACCOUNT_NR = :account ");
        }
        else if ("Both".equals(value)) {
          statement.append("AND (P.CREDIT_ACCOUNT_NR = :account OR P.DEBIT_ACCOUNT_NR = :account) ");
        }
      }
      if (formData.getAmountFrom().getValue() != null) {
        statement.append("AND AMOUNT >= :amountFrom");
      }
      if (formData.getAmountTo().getValue() != null) {
        statement.append("AND AMOUNT <= :amountTo ");
      }
      if (!StringUtility.isNullOrEmpty(formData.getPerson().getValue())) {
        statement.append("AND "
            + " (UPPER(CONCAT(COALESCE(P.FIRSTNAME, ''), ' ', COALESCE(P.NAME, ''))) LIKE UPPER(CONCAT('%',:person,'%')) "
            + "  OR UPPER(CONCAT(COALESCE(P.NAME, ''), ' ', COALESCE(P.FIRSTNAME, ''))) LIKE UPPER(CONCAT('%',:person,'%'))) ");
      }
      if (formData.getActivity().getValue() != null) {
        statement.append("AND ACTIVITY_NR = :activity");
      }
      if (formData.getStatus().getValue() != null) {
        statement.append("AND STATUS_UID = :status ");
      }
      if (!StringUtility.isNullOrEmpty(formData.getPostingText().getValue())) {
        statement.append("AND UPPER(POSTING_TEXT) LIKE UPPER(CONCAT('%',:postingText,'%')) ");
      }
    }
    PostingsTablePageData page = new PostingsTablePageData();
    SQL.selectInto(statement.toString() + POSTING_INTO_CLAUSE, formData, new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getAccountsTableData(Long accountingYearNr) {
    AccountsTablePageData page = new AccountsTablePageData();
    SQL.selectInto("" +
        "SELECT " +
        " ACCOUNT_NR, " +
        " ACCOUNT_NAME, " +
        " TYPE_UID, " +
        " DESCRIPTION " +
        "FROM ACCOUNT " +
        "WHERE :accountingYearNr = ACCOUNTING_YEAR_NR " +
        "INTO " +
        " :{page.accountNr}, " +
        " :{page.name}, " +
        " :{page.type}, " +
        " :{page.description} ", new NVPair("accountingYearNr", accountingYearNr), new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getAllAccountingYearTableData() {
    AccountingYearTablePageData page = new AccountingYearTablePageData();
    SQL.selectInto("" +
        "SELECT " +
        " ACCOUNTING_YEAR_NR, " +
        " NAME, " +
        " START_DATE, " +
        " END_DATE, " +
        " IS_CLOSED " +
        "FROM ACCOUNTING_YEAR " +
        "INTO " +
        " :{page.accountingYearNr}, " +
        " :{page.name}, " +
        " :{page.from}, " +
        " :{page.to}, " +
        " :{page.closed} ", new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getPostingsPerAccountTableData(Long accountNr) {
    PostingsPerAccountTablePageData page = new PostingsPerAccountTablePageData();
    SQL.selectInto("" +
        "SELECT " +
        " POSTING_NR, " +
        " VOUCHER_NO, " +
        " POSTING_DATE, " +
        " POSTING_TEXT, " +
        " AMOUNT, " +
        " NULL, " +
        " NULL, " +
        " 0 " +
        "FROM POSTING " +
        "WHERE DEBIT_ACCOUNT_NR = :accountNr " +
        " AND STATUS_UID = " + PostingStatusCodeType.EnteredStatusCode.ID +
        " " +
        "UNION SELECT " +
        " POSTING_NR, " +
        " VOUCHER_NO, " +
        " POSTING_DATE, " +
        " NULL, " +
        " NULL, " +
        " AMOUNT, " +
        " POSTING_TEXT, " +
        " 0 " +
        "FROM POSTING " +
        "WHERE CREDIT_ACCOUNT_NR = :accountNr " +
        " AND STATUS_UID = " + PostingStatusCodeType.EnteredStatusCode.ID +
        "INTO " +
        " :{page.postingNr}, " +
        " :{page.voucherNo}, " +
        " :{page.postingDate}, " +
        " :{page.postingTextDebit}, " +
        " :{page.amountDebit}, " +
        " :{page.amountCredit}, " +
        " :{page.postingTextCredit}, " +
        " :{page.columnSorting} ", new NVPair("accountNr", accountNr), new NVPair("page", page));
    return page;
  }
}

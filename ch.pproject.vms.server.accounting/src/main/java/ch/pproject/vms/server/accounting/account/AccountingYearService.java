package ch.pproject.vms.server.accounting.account;

import java.util.Date;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BooleanHolder;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.accounting.account.AccountingYearFormData;
import ch.pproject.vms.shared.accounting.account.CreateAccountingYearPermission;
import ch.pproject.vms.shared.accounting.account.IAccountService;
import ch.pproject.vms.shared.accounting.account.IAccountingYearService;
import ch.pproject.vms.shared.accounting.account.ReadAccountingYearPermission;
import ch.pproject.vms.shared.accounting.account.UpdateAccountingYearPermission;

public class AccountingYearService implements IAccountingYearService {

  @Override
  public AccountingYearFormData prepareCreate(AccountingYearFormData formData) {
    if (!ACCESS.check(new CreateAccountingYearPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return formData;
  }

  @Override
  public AccountingYearFormData create(AccountingYearFormData formData) {
    if (!ACCESS.check(new CreateAccountingYearPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (hasOverlap(formData.getAccountingYearNr(), formData.getYearFrom().getValue(), formData.getYearTo().getValue())) {
      throw new VetoException(TEXTS.get("ThereIsAnAccountingYearForThisPeriodAlready"));
    }

    Long accountingYearNr = SQL.getSequenceNextval(ISequences.VMSSEQ);

    if (formData.getAccountingYearNr() != null) {
      // create a copy all the accounts
      BEANS.get(IAccountService.class).copyAccountsToAccountingYear(formData.getAccountingYearNr(), accountingYearNr);
    }

    formData.setAccountingYearNr(accountingYearNr);

    SQL.insert("INSERT INTO ACCOUNTING_YEAR ( " + " ACCOUNTING_YEAR_NR, " + " NAME, " + " START_DATE, " + " END_DATE, " + " IS_CLOSED " + ") VALUES ( " + " :accountingYearNr, " + " :name, " + " :yearFrom, " + " :yearTo, " + " :closed " + ") ", formData);

    return formData;
  }

  @Override
  public AccountingYearFormData load(AccountingYearFormData formData) {
    if (!ACCESS.check(new ReadAccountingYearPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("SELECT NAME, " + " START_DATE, " + " END_DATE, " + " IS_CLOSED " + "FROM   ACCOUNTING_YEAR " + "WHERE  ACCOUNTING_YEAR_NR = :accountingYearNr " + "INTO   :name, " + "       :yearFrom, " + "       :yearTo, " + "       :closed ", formData);
    return formData;
  }

  @Override
  public AccountingYearFormData store(AccountingYearFormData formData) {
    if (!ACCESS.check(new UpdateAccountingYearPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (hasOverlap(formData.getAccountingYearNr(), formData.getYearFrom().getValue(), formData.getYearTo().getValue())) {
      throw new VetoException(TEXTS.get("ThereIsAnAccountingYearForThisPeriodAlready"));
    }

    if (isClosed(formData.getAccountingYearNr())) {
      throw new VetoException(TEXTS.get("TheSelectedAccountingYearIsAlreadyClosed"));
    }

    SQL.update("UPDATE ACCOUNTING_YEAR SET " + " NAME = :name, " + " START_DATE = :yearFrom, " + " END_DATE = :yearTo, " + " IS_CLOSED = :closed " + "WHERE  ACCOUNTING_YEAR_NR = :accountingYearNr ", formData);

    return formData;
  }

  @Override
  public Long getCurrentOrLatestAccountingYearNr() {
    LongHolder currentAccountingYearNr = new LongHolder();
    SQL.selectInto("SELECT ACCOUNTING_YEAR_NR " + "FROM   ACCOUNTING_YEAR " + "WHERE  SYSDATE() BETWEEN START_DATE AND COALESCE(END_DATE, '9999-12-31') " + "INTO   :currentAccountingYearNr ", new NVPair("currentAccountingYearNr", currentAccountingYearNr));

    if (currentAccountingYearNr.getValue() == null) {
      SQL.selectInto("SELECT AY.ACCOUNTING_YEAR_NR " + "FROM   ACCOUNTING_YEAR AY " + "WHERE  AY.START_DATE = (SELECT MAX(AY2.START_DATE) FROM ACCOUNTING_YEAR AY2)" + "INTO   :currentAccountingYearNr ", new NVPair("currentAccountingYearNr", currentAccountingYearNr));
    }

    return currentAccountingYearNr.getValue();
  }

  private boolean hasOverlap(Long accountingYearNr, Date startDate, Date endDate) {
    LongHolder overlap = new LongHolder();
    SQL.selectInto("SELECT COUNT(1) " + "FROM   ACCOUNTING_YEAR " + "WHERE  (ACCOUNTING_YEAR_NR <> :accountingYearNr OR :accountingYearNr IS NULL) " + "AND    START_DATE <= :endDate " + "AND    END_DATE >= :startDate  " + "INTO   :overlap ", new NVPair("accountingYearNr", accountingYearNr), new NVPair("overlap", overlap), new NVPair("startDate", startDate), new NVPair("endDate", endDate));
    return overlap.getValue() > 0;
  }

  @Override
  public boolean isClosed(Long accountingYearNr) {
    BooleanHolder isClosed = new BooleanHolder();
    SQL.selectInto("SELECT IS_CLOSED " + "FROM   ACCOUNTING_YEAR " + "WHERE  ACCOUNTING_YEAR_NR = :accountingYearNr " + "INTO   :isClosed ", new NVPair("accountingYearNr", accountingYearNr), new NVPair("isClosed", isClosed));
    return BooleanUtility.nvl(isClosed.getValue(), false);
  }
}

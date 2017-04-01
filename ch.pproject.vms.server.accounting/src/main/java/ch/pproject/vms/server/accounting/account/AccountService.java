package ch.pproject.vms.server.accounting.account;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.accounting.account.AccountFormData;
import ch.pproject.vms.shared.accounting.account.CreateAccountPermission;
import ch.pproject.vms.shared.accounting.account.IAccountService;
import ch.pproject.vms.shared.accounting.account.IAccountingYearService;
import ch.pproject.vms.shared.accounting.account.ReadAccountPermission;
import ch.pproject.vms.shared.accounting.account.UpdateAccountPermission;

public class AccountService implements IAccountService {

  @Override
  public AccountFormData prepareCreate(AccountFormData formData) {
    if (!ACCESS.check(new CreateAccountPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public AccountFormData create(AccountFormData formData) {
    if (!ACCESS.check(new CreateAccountPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (BEANS.get(IAccountingYearService.class).isClosed(formData.getAccountingYear().getValue())) {
      throw new VetoException(TEXTS.get("TheSelectedAccountingYearIsAlreadyClosed"));
    }

    formData.setAccountNr(SQL.getSequenceNextval(ISequences.VMSSEQ));

    SQL.insert("INSERT INTO ACCOUNT ( " + "	ACCOUNT_NR, " + " ACCOUNT_NAME, " + " TYPE_UID, " + "	DESCRIPTION, " + " ACCOUNTING_YEAR_NR " + ") VALUES ( " + "	:accountNr, " + "	:name, " + "	:type, " + "	:description, " + " :accountingYear " + ") ", formData);

    return formData;
  }

  @Override
  public AccountFormData load(AccountFormData formData) {
    if (!ACCESS.check(new ReadAccountPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("SELECT ACCOUNT_NAME, " + " TYPE_UID, " + "	DESCRIPTION, " + " ACCOUNTING_YEAR_NR " + "FROM 	ACCOUNT " + "WHERE  ACCOUNT_NR = :accountNr " + "INTO   :name, " + " 		  :type, " + "		    :description, " + "       :accountingYear ", formData);
    return formData;
  }

  @Override
  public AccountFormData store(AccountFormData formData) {
    if (!ACCESS.check(new UpdateAccountPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (BEANS.get(IAccountingYearService.class).isClosed(formData.getAccountingYear().getValue())) {
      throw new VetoException(TEXTS.get("TheSelectedAccountingYearIsAlreadyClosed"));
    }

    SQL.update("UPDATE ACCOUNT SET " + " ACCOUNT_NAME = :name, " + " TYPE_UID = COALESCE(:type, 0), " + "	DESCRIPTION = :description, " + " ACCOUNTING_YEAR_NR = : " + "WHERE  ACCOUNT_NR = :accountNr ", formData);
    return formData;
  }

  @Override
  public void copyAccountsToAccountingYear(Long oldAccountingYearNr, Long newAccountingYearNr) {

    if (BEANS.get(IAccountingYearService.class).isClosed(newAccountingYearNr)) {
      throw new VetoException(TEXTS.get("TheSelectedAccountingYearIsAlreadyClosed"));
    }

    SQL.insert("INSERT INTO ACCOUNT ( " + " ACCOUNT_NR, " + " ACCOUNT_NAME, " + " TYPE_UID, " + " DESCRIPTION, " + " ACCOUNTING_YEAR_NR " + ") " + "SELECT nextval('" + ISequences.VMSSEQ + "'), " + " ACCOUNT_NAME, " + " TYPE_UID, " + " DESCRIPTION, " + " :newAccountingYearNr " + "FROM ACCOUNT " + "WHERE ACCOUNTING_YEAR_NR = :oldAccountingYearNr ", new NVPair("oldAccountingYearNr", oldAccountingYearNr), new NVPair("newAccountingYearNr", newAccountingYearNr));
  }
}

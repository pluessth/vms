package ch.pproject.vms.server.accounting.posting;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.accounting.account.IAccountingYearService;
import ch.pproject.vms.shared.accounting.posting.CreatePostingPermission;
import ch.pproject.vms.shared.accounting.posting.IPostingService;
import ch.pproject.vms.shared.accounting.posting.PostingFormData;
import ch.pproject.vms.shared.accounting.posting.ReadPostingPermission;
import ch.pproject.vms.shared.accounting.posting.UpdatePostingPermission;

public class PostingService implements IPostingService {

  @Override
  public PostingFormData prepareCreate(PostingFormData formData) {
    if (!ACCESS.check(new CreatePostingPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData.getAccountingYear().setValue(BEANS.get(IAccountingYearService.class).getCurrentOrLatestAccountingYearNr());
    return formData;
  }

  @Override
  public PostingFormData create(PostingFormData formData) {
    if (!ACCESS.check(new CreatePostingPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData.setPostingNr(SQL.getSequenceNextval(ISequences.VMSSEQ));

    SQL.insert("" +
        "INSERT INTO POSTING ( " +
        " POSTING_NR, " +
        " VOUCHER_NO, " +
        " STATUS_UID, " +
        " POSTING_DATE, " +
        " ACCOUNTING_YEAR_NR, " +
        " CREDIT_ACCOUNT_NR, " +
        " DEBIT_ACCOUNT_NR, " +
        " AMOUNT, " +
        " PERSON_NR, " +
        " ACTIVITY_NR, " +
        " POSTING_TEXT " +
        ") VALUES ( " +
        " :postingNr, " +
        " :voucherNo, " +
        " COALESCE(:status, 0), " +
        " :postingDate, " +
        " COALESCE(:accountingYear, 0), " +
        " COALESCE(:creditAccount, 0), " +
        " COALESCE(:debitAccount, 0), " +
        " :amount, " +
        " COALESCE(:person, 0), " +
        " COALESCE(:activity, 0), " +
        " :postingText " +
        ") ", formData);

    return formData;
  }

  @Override
  public PostingFormData load(PostingFormData formData) {
    if (!ACCESS.check(new ReadPostingPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto("" +
        "SELECT VOUCHER_NO, " +
        " STATUS_UID, " +
        " POSTING_DATE, " +
        " ACCOUNTING_YEAR_NR, " +
        " CREDIT_ACCOUNT_NR, " +
        " DEBIT_ACCOUNT_NR, " +
        " AMOUNT, " +
        " PERSON_NR, " +
        " ACTIVITY_NR, " +
        " POSTING_TEXT " +
        "FROM   POSTING " +
        "WHERE  POSTING_NR = :postingNr " +
        "INTO   " +
        " :voucherNo, " +
        " :status, " +
        " :postingDate, " +
        " :accountingYear, " +
        " :creditAccount, " +
        " :debitAccount, " +
        " :amount, " +
        " :person, " +
        " :activity, " +
        " :postingText ", formData);

    return formData;
  }

  @Override
  public PostingFormData store(PostingFormData formData) {
    if (!ACCESS.check(new UpdatePostingPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("" +
        "UPDATE POSTING SET " +
        " VOUCHER_NO = :voucherNo, " +
        " STATUS_UID = COALESCE(:status, 0), " +
        " POSTING_DATE = :postingDate, " +
        " ACCOUNTING_YEAR_NR = COALESCE(:accountingYear, 0), " +
        " CREDIT_ACCOUNT_NR = COALESCE(:creditAccount, 0), " +
        " DEBIT_ACCOUNT_NR = COALESCE(:debitAccount, 0), " +
        " AMOUNT = :amount, " +
        " PERSON_NR = COALESCE(:person, 0), " +
        " ACTIVITY_NR = COALESCE(:activity, 0), " +
        " POSTING_TEXT = :postingText " +
        "WHERE  POSTING_NR = :postingNr ", formData);
    return formData;
  }

  @Override
  public void storeSilent(AbstractFormData formData) {
    if (formData == null) {
      return;
    }
    if (formData instanceof PostingFormData) {
      create((PostingFormData) formData);
    }
    else {
      throw new ProcessingException("Not possible to call " + getClass().getName() + ".storeSilent(AbstractFormData)' with an formData of type " + formData.getClass());
    }
  }
}

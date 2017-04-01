package ch.pproject.vms.shared.accounting.account;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.vms.client.accounting.account.AccountingYearForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class AccountingYearFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  /**
   * access method for property AccountingYearNr.
   */
  public Long getAccountingYearNr() {
    return getAccountingYearNrProperty().getValue();
  }

  /**
   * access method for property AccountingYearNr.
   */
  public void setAccountingYearNr(Long accountingYearNr) {
    getAccountingYearNrProperty().setValue(accountingYearNr);
  }

  public AccountingYearNrProperty getAccountingYearNrProperty() {
    return getPropertyByClass(AccountingYearNrProperty.class);
  }

  public Closed getClosed() {
    return getFieldByClass(Closed.class);
  }

  public Name getName() {
    return getFieldByClass(Name.class);
  }

  public YearFrom getYearFrom() {
    return getFieldByClass(YearFrom.class);
  }

  public YearTo getYearTo() {
    return getFieldByClass(YearTo.class);
  }

  public static class AccountingYearNrProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class Closed extends AbstractValueFieldData<Boolean> {

    private static final long serialVersionUID = 1L;
  }

  public static class Name extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class YearFrom extends AbstractValueFieldData<Date> {

    private static final long serialVersionUID = 1L;
  }

  public static class YearTo extends AbstractValueFieldData<Date> {

    private static final long serialVersionUID = 1L;
  }
}
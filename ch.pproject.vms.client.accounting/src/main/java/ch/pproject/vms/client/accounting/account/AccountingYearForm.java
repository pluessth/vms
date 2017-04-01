package ch.pproject.vms.client.accounting.account;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.CancelButton;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox.ClosedField;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox.NameField;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox.YearBox;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox.YearBox.YearFrom;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.GroupBox.YearBox.YearTo;
import ch.pproject.vms.client.accounting.account.AccountingYearForm.MainBox.OkButton;
import ch.pproject.vms.shared.accounting.account.AccountingYearFormData;
import ch.pproject.vms.shared.accounting.account.IAccountingYearService;
import ch.pproject.vms.shared.accounting.account.UpdateAccountingYearPermission;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("cde19317-b053-4657-b298-946b716fec26")
@FormData(value = AccountingYearFormData.class, sdkCommand = SdkCommand.CREATE)
public class AccountingYearForm extends AbstractForm {

  private Long m_accountingYearNr;

  public AccountingYearForm() {
    super();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AccountingYear");
  }

  @FormData
  public Long getAccountingYearNr() {
    return m_accountingYearNr;
  }

  @FormData
  public void setAccountingYearNr(Long accountingYearNr) {
    m_accountingYearNr = accountingYearNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the ClosedField
   */
  public ClosedField getClosedField() {
    return getFieldByClass(ClosedField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the YearTo
   */
  public YearTo getYearTo() {
    return getFieldByClass(YearTo.class);
  }

  /**
   * @return the YearBox
   */
  public YearBox getYearBox() {
    return getFieldByClass(YearBox.class);
  }

  /**
   * @return the YearFrom
   */
  public YearFrom getYearFrom() {
    return getFieldByClass(YearFrom.class);
  }

  @Order(10.0)
  @ClassId("e37003f6-89bb-42ed-9541-c7d0cf335751")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("baf06e80-a503-48ba-a39d-92052197bc6c")
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("d6f708c8-a575-480e-8b01-8d32670ee67c")
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 100;
        }
      }

      @Order(20.0)
      @ClassId("38073041-f065-4b2b-a9ba-ad44e0c94fa6")
      public class ClosedField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Closed");
        }
      }

      @Order(30.0)
      @ClassId("b3c6f761-3f4d-42b6-9635-bfc0ceed2665")
      public class YearBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Year");
        }

        @Order(10.0)
        @ClassId("9c441abe-62c7-4a32-ac79-5899574fb329")
        public class YearFrom extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(20.0)
        @ClassId("1a162d4e-121c-498b-b278-680c38297a7e")
        public class YearTo extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

    }

    @Order(20.0)
    @ClassId("042fbbce-4992-4698-aa1a-42940cc56678")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("02774904-5337-48db-83d5-a46e9704eb0a")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IAccountingYearService service = BEANS.get(IAccountingYearService.class);
      AccountingYearFormData formData = new AccountingYearFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateAccountingYearPermission());
    }

    @Override
    public void execStore() {
      IAccountingYearService service = BEANS.get(IAccountingYearService.class);
      AccountingYearFormData formData = new AccountingYearFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IAccountingYearService service = BEANS.get(IAccountingYearService.class);
      AccountingYearFormData formData = new AccountingYearFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IAccountingYearService service = BEANS.get(IAccountingYearService.class);
      AccountingYearFormData formData = new AccountingYearFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}

package ch.pproject.vms.client.accounting.account;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.CancelButton;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.GroupBox;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.GroupBox.AccountingYearField;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.GroupBox.DescriptionField;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.GroupBox.NameField;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.GroupBox.TypeField;
import ch.pproject.vms.client.accounting.account.AccountForm.MainBox.OkButton;
import ch.pproject.vms.shared.accounting.account.AccountFormData;
import ch.pproject.vms.shared.accounting.account.AccountTypeCodeType;
import ch.pproject.vms.shared.accounting.account.AccountingYearLookupCall;
import ch.pproject.vms.shared.accounting.account.IAccountService;
import ch.pproject.vms.shared.accounting.account.UpdateAccountPermission;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("c305421f-f469-4064-8d19-ff4f13425419")
@FormData(value = AccountFormData.class, sdkCommand = SdkCommand.CREATE)
public class AccountForm extends AbstractForm {

  private Long m_accountNr;

  public AccountForm() {
    super();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Account");
  }

  @FormData
  public Long getAccountNr() {
    return m_accountNr;
  }

  @FormData
  public void setAccountNr(Long accountNr) {
    m_accountNr = accountNr;
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

  public AccountingYearField getAccountingYearField() {
    return getFieldByClass(AccountingYearField.class);
  }

  public DescriptionField getDescriptionField() {
    return getFieldByClass(DescriptionField.class);
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

  public TypeField getTypeField() {
    return getFieldByClass(TypeField.class);
  }

  @Order(10.0)
  @ClassId("2c901d4d-3304-458f-a68a-01b9aa7f767e")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("9b3241a6-f2df-41de-a28a-7db351a739fd")
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("b1f2cbcb-5431-4021-8fbe-21f48ddf94b2")
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
      @ClassId("dfe92d10-fd7b-4e59-9c8d-e72e24fe3c00")
      public class TypeField extends AbstractSmartField<Long> {

        @Override
        protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
          return AccountTypeCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Type");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(30.0)
      @ClassId("e167b5f6-e159-4bce-9e45-e522d067116b")
      public class AccountingYearField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AccountingYear");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return AccountingYearLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(40.0)
      @ClassId("b01167d7-7b7a-4d12-9e86-cc9221d28912")
      public class DescriptionField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Description");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 1000;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }
    }

    @Order(20.0)
    @ClassId("47e97c14-81b2-44de-b5b5-4873da33127b")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("444d033c-584c-4a41-9cd1-8ca1e6ee1d83")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IAccountService service = BEANS.get(IAccountService.class);
      AccountFormData formData = new AccountFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateAccountPermission());
    }

    @Override
    public void execStore() {
      IAccountService service = BEANS.get(IAccountService.class);
      AccountFormData formData = new AccountFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IAccountService service = BEANS.get(IAccountService.class);
      AccountFormData formData = new AccountFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IAccountService service = BEANS.get(IAccountService.class);
      AccountFormData formData = new AccountFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}

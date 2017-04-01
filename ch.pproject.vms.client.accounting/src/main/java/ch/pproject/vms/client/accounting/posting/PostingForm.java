package ch.pproject.vms.client.accounting.posting;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.CancelButton;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.AccountingYearField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.AmountField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.CreditAccountField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.DebitAccountField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.PostingDateField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.PostingTextField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.StatusField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.GroupBox.VoucherNoField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.OkButton;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.TabBox;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.TabBox.AdditionalInformationBox;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.TabBox.AdditionalInformationBox.ActivityField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.TabBox.AdditionalInformationBox.PersonField;
import ch.pproject.vms.client.accounting.posting.PostingForm.MainBox.TabBox.NotesBox;
import ch.pproject.vms.shared.accounting.account.AccountLookupCall;
import ch.pproject.vms.shared.accounting.account.AccountingYearLookupCall;
import ch.pproject.vms.shared.accounting.posting.IPostingService;
import ch.pproject.vms.shared.accounting.posting.PostingFormData;
import ch.pproject.vms.shared.accounting.posting.UpdatePostingPermission;
import ch.pproject.vms.shared.accounting.services.code.PostingStatusCodeType;
import ch.pproject.vms.shared.core.entityimport.annotation.ImportableEntity;
import ch.pproject.vms.shared.core.person.PersonLookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("80d535c5-fc7a-473a-a1df-72376bb08d9c")
@ImportableEntity(importServiceInterface = IPostingService.class)
@FormData(value = PostingFormData.class, sdkCommand = SdkCommand.CREATE)
public class PostingForm extends AbstractForm {

  private Long m_postingNr;

  public PostingForm() {
    super();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Posting");
  }

  @FormData
  public Long getPostingNr() {
    return m_postingNr;
  }

  @FormData
  public void setPostingNr(Long postingNr) {
    m_postingNr = postingNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public ActivityField getActivityField() {
    return getFieldByClass(ActivityField.class);
  }

  public AmountField getAmountField() {
    return getFieldByClass(AmountField.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public AccountingYearField getAccountingYearField() {
    return getFieldByClass(AccountingYearField.class);
  }

  public CreditAccountField getCreditAccountField() {
    return getFieldByClass(CreditAccountField.class);
  }

  public DebitAccountField getDebitAccountField() {
    return getFieldByClass(DebitAccountField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public AdditionalInformationBox getAdditionalInformationBox() {
    return getFieldByClass(AdditionalInformationBox.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PersonField getPersonField() {
    return getFieldByClass(PersonField.class);
  }

  public PostingDateField getPostingDateField() {
    return getFieldByClass(PostingDateField.class);
  }

  public PostingTextField getPostingTextField() {
    return getFieldByClass(PostingTextField.class);
  }

  public StatusField getStatusField() {
    return getFieldByClass(StatusField.class);
  }

  public VoucherNoField getVoucherNoField() {
    return getFieldByClass(VoucherNoField.class);
  }

  @Order(10.0)
  @ClassId("39d838f4-8a95-4efe-aebb-2a55abfdbdf5")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("c680f10d-274c-4d9e-a38a-59a3bad0cb67")
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      @ClassId("a60193b4-27c9-4cae-8957-e67a201177f5")
      public class AccountingYearField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AccountingYear");
        }

        @Override
        protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
          return AccountingYearLookupCall.class;

        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      @ClassId("0b5234a9-cae1-4205-a359-fb8a883c37f0")
      public class DebitAccountField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DebitAccount");
        }

        @Override
        protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
          return AccountLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return AccountingYearField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }
      }

      @Order(30.0)
      @ClassId("cd30aad1-b7db-43c2-a867-2886174b3086")
      public class CreditAccountField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CreditAccount");
        }

        @Override
        protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
          return AccountLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return AccountingYearField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }
      }

      @Order(40.0)
      @ClassId("e6accf7d-4782-4159-ae21-23a5054c258f")
      public class AmountField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Amount");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(50.0)
      @ClassId("488daf98-1e1c-4d5a-87fa-8a12721c2649")
      public class VoucherNoField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VoucherNo");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 100;
        }
      }

      @Order(60.0)
      @ClassId("d7eea383-108e-4b3c-a7e6-41d5a28320ec")
      public class PostingDateField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PostingDate");
        }
      }

      @Order(70.0)
      @ClassId("ca676430-e8e0-4fa2-b51e-fdda1211c891")
      public class StatusField extends AbstractSmartField<Long> {

        @Override
        protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
          return PostingStatusCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Status");
        }
      }

      @Order(80.0)
      @ClassId("64f8d4fd-d10f-4f55-8502-07bc2a023fb6")
      public class PostingTextField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PostingText");
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

    @Order(30)
    @ClassId("a9a22710-bcfa-424d-9fb1-fc88f6a39f40")
    public class TabBox extends AbstractTabBox {

      @Order(10)
      @ClassId("8f58f7dd-4cfa-4450-9e1d-69638ea4bd4b")
      public class AdditionalInformationBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AdditionalInformation");
        }

        @Order(10.0)
        @ClassId("78986bd0-03bd-4817-acdd-1d833745bb74")
        public class PersonField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Person");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return PersonLookupCall.class;
          }
        }

        @Order(20.0)
        @ClassId("8ab602f9-6920-4539-ba98-4d0e6a2e5658")
        public class ActivityField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Activity");
          }
        }
      }

      @Order(20.0)
      @ClassId("96a7408b-c89b-4d6b-8b12-5011f7dae1cd")
      public class NotesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Order(10.0)
        @ClassId("a7b44d3b-f732-4801-aa4e-cd8e48aa0b19")
        public class NotesField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 1000;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }
        }
      }
    }

    @Order(50.0)
    @ClassId("7b6ecfa6-60da-45d5-8d26-2ece9e1ea82b")
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
    @ClassId("02b05757-65e4-4f20-8e68-a7285f95b2ac")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IPostingService service = BEANS.get(IPostingService.class);
      PostingFormData formData = new PostingFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdatePostingPermission());
    }

    @Override
    public void execStore() {
      IPostingService service = BEANS.get(IPostingService.class);
      PostingFormData formData = new PostingFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IPostingService service = BEANS.get(IPostingService.class);
      PostingFormData formData = new PostingFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      formData.getStatus().setValue(PostingStatusCodeType.EnteredStatusCode.ID);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IPostingService service = BEANS.get(IPostingService.class);
      PostingFormData formData = new PostingFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}

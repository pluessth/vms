package ch.pproject.vms.client.accounting.posting;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountRadioButtonGroup;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountRadioButtonGroup.BothButton;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountRadioButtonGroup.CreditButton;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountRadioButtonGroup.DebitButton;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AccountingYearField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.ActivityField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AmountBox;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AmountBox.AmountFrom;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.AmountBox.AmountTo;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.PersonField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.PostingDateBox;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.PostingDateBox.PostingDateFrom;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.PostingDateBox.PostingDateTo;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.PostingTextField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.StatusField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.FieldBox.VoucherNoField;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.ResetButton;
import ch.pproject.vms.client.accounting.posting.PostingSearchForm.MainBox.SearchButton;
import ch.pproject.vms.shared.accounting.account.AccountLookupCall;
import ch.pproject.vms.shared.accounting.account.AccountingYearLookupCall;
import ch.pproject.vms.shared.accounting.posting.PostingSearchFormData;
import ch.pproject.vms.shared.accounting.services.code.PostingStatusCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("347bdd3b-99ce-4928-adab-acabacbd708e")
@FormData(value = PostingSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class PostingSearchForm extends AbstractSearchForm {

  public PostingSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Posting");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    PostingSearchFormData formData = new PostingSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  public AccountField getAccountField() {
    return getFieldByClass(AccountField.class);
  }

  public AccountRadioButtonGroup getAccountRadioButtonGroup() {
    return getFieldByClass(AccountRadioButtonGroup.class);
  }

  public ActivityField getActivityField() {
    return getFieldByClass(ActivityField.class);
  }

  public AmountBox getAmountBox() {
    return getFieldByClass(AmountBox.class);
  }

  public AmountFrom getAmountFrom() {
    return getFieldByClass(AmountFrom.class);
  }

  public AmountTo getAmountTo() {
    return getFieldByClass(AmountTo.class);
  }

  public BothButton getBothButton() {
    return getFieldByClass(BothButton.class);
  }

  public AccountingYearField getAccountingYearField() {
    return getFieldByClass(AccountingYearField.class);
  }

  public CreditButton getCreditButton() {
    return getFieldByClass(CreditButton.class);
  }

  public DebitButton getDebitButton() {
    return getFieldByClass(DebitButton.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public PersonField getPersonField() {
    return getFieldByClass(PersonField.class);
  }

  public PostingDateBox getPostingDateBox() {
    return getFieldByClass(PostingDateBox.class);
  }

  public PostingDateFrom getPostingDateFrom() {
    return getFieldByClass(PostingDateFrom.class);
  }

  public PostingDateTo getPostingDateTo() {
    return getFieldByClass(PostingDateTo.class);
  }

  public PostingTextField getPostingTextField() {
    return getFieldByClass(PostingTextField.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public StatusField getStatusField() {
    return getFieldByClass(StatusField.class);
  }

  public VoucherNoField getVoucherNoField() {
    return getFieldByClass(VoucherNoField.class);
  }

  @Order(10.0)
  @ClassId("f34f3f00-34c4-4f92-bbdd-a9da86207014")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("a1254e87-0e93-49df-9aba-432ca6245474")
    public class FieldBox extends AbstractGroupBox {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      @ClassId("6c8a882c-9c8c-4d92-a2a4-f95472511c4e")
      public class VoucherNoField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VoucherNo");
        }
      }

      @Order(20.0)
      @ClassId("a988b834-4235-42ed-bf7d-343ab8de7bf5")
      public class AccountingYearField extends AbstractSmartField<Long> {

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call) {
          ((AccountingYearLookupCall) call).setShowClosedAccountingYears(true);
        }

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

      @Order(30.0)
      @ClassId("02aaa45f-d4e0-4576-b04f-76cf0c0b308d")
      public class AccountField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Account");
        }

        @Override
        protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
          return AccountLookupCall.class;
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
      @ClassId("6446440f-77fd-47f7-ac10-a62e086aba6c")
      public class AccountRadioButtonGroup extends AbstractRadioButtonGroup<String> {

        @Override
        protected void execInitField() {
          setValue("Both");
        }

        @Order(10.0)
        @ClassId("fc7bc5e5-fe9d-4798-9e1a-94fcc24bd04c")
        public class DebitButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Debit");
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return "Debit";
          }
        }

        @Order(20.0)
        @ClassId("28152e8c-87b5-4e14-b6dc-5b99c8cabbd4")
        public class CreditButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Credit");
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return "Credit";
          }
        }

        @Order(30.0)
        @ClassId("8992b359-8c04-4ad2-86bc-6a3bbb95d9a0")
        public class BothButton extends AbstractRadioButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Both");
          }

          @Override
          protected Object getConfiguredRadioValue() {
            return "Both";
          }
        }
      }

      @Order(50.0)
      @ClassId("b5be173b-8087-428b-957c-ec7872778f54")
      public class AmountBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Amount");
        }

        @Order(10.0)
        @ClassId("14c4f934-152b-4eee-83a8-e9abd47688d2")
        public class AmountFrom extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20.0)
        @ClassId("07d0612e-9c0a-47ce-b8cd-5e9a53ac8047")
        public class AmountTo extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(60.0)
      @ClassId("f3323b2d-c8fd-475c-a7ab-52c15b69fd24")
      public class PostingDateBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PostingDate");
        }

        @Order(10.0)
        @ClassId("1d4cb9b0-226d-4134-a613-22063927be32")
        public class PostingDateFrom extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("from");
          }
        }

        @Order(20.0)
        @ClassId("92b9a406-2429-436a-b012-2f606dae84e6")
        public class PostingDateTo extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(70.0)
      @ClassId("4b2b2003-9ac5-4013-8288-4a32f083ca55")
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
      @ClassId("3015f30c-1202-42f4-8b69-5d0294d78f48")
      public class PersonField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Person");
        }
      }

      @Order(90.0)
      @ClassId("1a136397-d61a-4a6a-9817-004beee0b9cc")
      public class ActivityField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Activity");
        }
      }

      @Order(100.0)
      @ClassId("69200af1-f08e-41d2-96ef-07121424fff1")
      public class PostingTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PostingText");
        }
      }
    }

    @Order(20.0)
    @ClassId("298e4537-4d27-4a76-8fef-7999fb4fe931")
    public class ResetButton extends AbstractResetButton {
    }

    @Order(30.0)
    @ClassId("0b05a013-4c0b-4be8-a006-7225ba18a095")
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {

  }
}

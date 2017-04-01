package ch.pproject.vms.client.core.person;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.ResetButton;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.SearchButton;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.DateOfBirthBox;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.DateOfBirthBox.DateOfBirthFrom;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.DateOfBirthBox.DateOfBirthTo;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.NameField;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.PhoneField;
import ch.pproject.vms.client.core.person.PersonSearchForm.MainBox.TabBox.FieldBox.SexField;
import ch.pproject.vms.shared.core.person.PersonSearchFormData;
import ch.pproject.vms.shared.core.services.code.SexCodeType;

@ClassId("4e3e3779-e13d-44d2-ba9b-93d62eef3cd7")
@FormData(value = PersonSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PersonSearchForm extends AbstractSearchForm {

  private Long m_roleUid;

  public PersonSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    PersonSearchFormData formData = new PersonSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  public DateOfBirthBox getDateOfBirthBox() {
    return getFieldByClass(DateOfBirthBox.class);
  }

  public DateOfBirthFrom getDateOfBirthFrom() {
    return getFieldByClass(DateOfBirthFrom.class);
  }

  public DateOfBirthTo getDateOfBirthTo() {
    return getFieldByClass(DateOfBirthTo.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public SexField getSexField() {
    return getFieldByClass(SexField.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @FormData
  public Long getRoleUid() {
    return m_roleUid;
  }

  @FormData
  public void setRoleUid(Long roleUid) {
    m_roleUid = roleUid;
  }

  @Order(10.0)
  @ClassId("232fed98-3f83-4c42-bd78-3444b015f4d0")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("a352c1bc-6373-45f1-93af-97482079d41f")
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      @ClassId("f3dc1ef6-07b7-4566-a0ff-8fa012970ece")
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(10.0)
        @ClassId("828f1062-0818-4519-9a00-6a627ea1ad8e")
        public class NameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Name");
          }
        }

        @Order(20.0)
        @ClassId("e32fec6e-a5a8-43fa-b143-0d3506aff650")
        public class DateOfBirthBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DateOfBirth");
          }

          @Order(10.0)
          @ClassId("a0d27297-dbe5-425b-b268-133985feb3f0")
          public class DateOfBirthFrom extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          @ClassId("e0c70076-1385-46fc-882f-7c6854c403dd")
          public class DateOfBirthTo extends AbstractDateField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30.0)
        @ClassId("1c929b59-9f30-4d9a-967a-2bd4c4d1397d")
        public class PhoneField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(40.0)
        @ClassId("b85acc5c-2d5a-4b11-9dbf-2222d1d39795")
        public class SexField extends AbstractListBox<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return SexCodeType.class;
          }

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Sex");
          }
        }
      }
    }

    @Order(130.0)
    @ClassId("b122965b-ac5e-44f3-bde3-2060d52a68fd")
    public class ResetButton extends AbstractResetButton {
    }

    @Order(140.0)
    @ClassId("5d2ac4c4-e1a2-413d-8ba4-d4599d501c9e")
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}

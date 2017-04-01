package ch.pproject.vms.client.core.person;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import ch.pproject.vms.client.core.person.PersonForm.MainBox.AddressBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.AddressBox.CityField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.AddressBox.StreetField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.AddressBox.ZipField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.ContactBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.ContactBox.EMailAddressField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.ContactBox.PhoneMobileField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.ContactBox.PhonePrivateField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.ContactBox.PhoneWorkField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.HeaderBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.HeaderBox.DateOfBirthField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.HeaderBox.FirstNameField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.HeaderBox.NameField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.HeaderBox.SexField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.LicenseBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.LicenseBox.PlayerLicenseNumberField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.LicenseBox.RefereeLicenseNumberField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.OkButton;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.TabBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.TabBox.NotesBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.TabBox.NotesBox.NotesField;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.TabBox.RolesBox;
import ch.pproject.vms.client.core.person.PersonForm.MainBox.TabBox.RolesBox.RolesField;
import ch.pproject.vms.shared.core.entityimport.annotation.ImportableEntity;
import ch.pproject.vms.shared.core.person.IPersonService;
import ch.pproject.vms.shared.core.person.PersonFormData;
import ch.pproject.vms.shared.core.person.RoleCodeType;
import ch.pproject.vms.shared.core.person.SexCodeType;
import ch.pproject.vms.shared.core.person.UpdatePersonPermission;

@ClassId("56403a66-0945-449f-8d9e-95924321e7c6")
@ImportableEntity(importServiceInterface = IPersonService.class)
@FormData(value = PersonFormData.class, sdkCommand = SdkCommand.CREATE)
public class PersonForm extends AbstractForm {

  private Long m_personNr;

  public PersonForm() {
    super();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  @FormData
  public Long getPersonNr() {
    return m_personNr;
  }

  @FormData
  public void setPersonNr(Long personNr) {
    m_personNr = personNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public AddressBox getAddressBox() {
    return getFieldByClass(AddressBox.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  public ContactBox getContactBox() {
    return getFieldByClass(ContactBox.class);
  }

  public DateOfBirthField getDateOfBirthField() {
    return getFieldByClass(DateOfBirthField.class);
  }

  public EMailAddressField getEMailAddressField() {
    return getFieldByClass(EMailAddressField.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public HeaderBox getHeaderBox() {
    return getFieldByClass(HeaderBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  public LicenseBox getLicenseBox() {
    return getFieldByClass(LicenseBox.class);
  }

  public PlayerLicenseNumberField getPlayerLicenseNumberField() {
    return getFieldByClass(PlayerLicenseNumberField.class);
  }

  public RefereeLicenseNumberField getRefereeLicenseNumberField() {
    return getFieldByClass(RefereeLicenseNumberField.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PhoneMobileField getPhoneMobileField() {
    return getFieldByClass(PhoneMobileField.class);
  }

  public PhonePrivateField getPhonePrivateField() {
    return getFieldByClass(PhonePrivateField.class);
  }

  public PhoneWorkField getPhoneWorkField() {
    return getFieldByClass(PhoneWorkField.class);
  }

  public RolesBox getRolesBox() {
    return getFieldByClass(RolesBox.class);
  }

  public RolesField getRolesField() {
    return getFieldByClass(RolesField.class);
  }

  public SexField getSexField() {
    return getFieldByClass(SexField.class);
  }

  public StreetField getStreetField() {
    return getFieldByClass(StreetField.class);
  }

  public ZipField getZipField() {
    return getFieldByClass(ZipField.class);
  }

  @Order(10.0)
  @ClassId("ccc16339-4020-47b6-be4b-2ae44004f543")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("6182c32f-7f14-4305-96a0-b8259783bf80")
    public class HeaderBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("e36426b0-4b22-45ab-99d9-9b1b61c669cb")
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }
      }

      @Order(20.0)
      @ClassId("366786ef-2c71-49cd-920e-74a02db6680d")
      public class FirstNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }
      }

      @Order(30.0)
      @ClassId("0d7863da-6298-464d-a43f-99f2eb16f9df")
      public class SexField extends AbstractSmartField<Long> {

        @Override
        protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
          return SexCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Sex");
        }
      }

      @Order(40.0)
      @ClassId("e23e0462-49b5-4d69-9385-4768906c505d")
      public class DateOfBirthField extends AbstractDateField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DateOfBirth");
        }
      }
    }

    @Order(20.0)
    @ClassId("35023d8c-b10b-4f8f-b1c4-3b92c492d3c7")
    public class AddressBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Address");
      }

      @Order(10.0)
      @ClassId("c720b518-c783-4d00-8285-559b1205a335")
      public class StreetField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Street");
        }
      }

      @Order(20.0)
      @ClassId("b98946c4-00b2-4802-b2b2-64505d03c5b0")
      public class ZipField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ZIP");
        }
      }

      @Order(30.0)
      @ClassId("66205952-d9d7-44f0-aa8d-32b9a5caf4d1")
      public class CityField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Place");
        }
      }
    }

    @Order(30.0)
    @ClassId("81d8f12d-331f-4e3c-a545-38477efbd56e")
    public class ContactBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Contact");
      }

      @Order(10.0)
      @ClassId("043774ad-a5e0-4cb7-b1e0-95674223c69c")
      public class PhonePrivateField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PhonePrivate");
        }
      }

      @Order(20.0)
      @ClassId("da65a565-52a8-4fff-b349-79a8d88050b0")
      public class PhoneWorkField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PhoneWork");
        }
      }

      @Order(30.0)
      @ClassId("a7c2bc41-5a0f-4fb4-b495-76411085d428")
      public class PhoneMobileField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PhoneMobile");
        }
      }

      @Order(40.0)
      @ClassId("f9e3b47b-6de4-49d2-bc6a-42b4f9cdfea4")
      public class EMailAddressField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("EMailAddress");
        }
      }
    }

    @Order(32)
    public class LicenseBox extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Licenses");
      }

      @Order(10)
      public class PlayerLicenseNumberField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Player");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 60;
        }
      }

      @Order(20)
      public class RefereeLicenseNumberField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Referee");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 60;
        }
      }
    }

    @Order(35)
    @ClassId("53c9bcfd-ac23-4246-bfab-8f7dab08b021")
    public class TabBox extends AbstractTabBox {

      @Order(10)
      @ClassId("721f6b5b-ba75-48de-b83b-7d44ddd4dc77")
      public class RolesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Roles");
        }

        @Order(10)
        @ClassId("66944426-ac7d-4076-ac84-40f4b2aee526")
        public class RolesField extends AbstractTableField<RolesField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(10.0)
          @ClassId("9f6ccd54-77c6-4c66-8463-4bb1eb8f6db8")
          public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            public NotesColumn getNotesColumn() {
              return getColumnSet().getColumnByClass(NotesColumn.class);
            }

            public FromColumn getFromColumn() {
              return getColumnSet().getColumnByClass(FromColumn.class);
            }

            public ToColumn getToColumn() {
              return getColumnSet().getColumnByClass(ToColumn.class);
            }

            public RoleColumn getRoleColumn() {
              return getColumnSet().getColumnByClass(RoleColumn.class);
            }

            @Order(10.0)
            @ClassId("30b9fb5f-ab8f-40f6-a0d4-fb71b24714b5")
            public class RoleColumn extends AbstractSmartColumn<Long> {

              @Override
              protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
                return RoleCodeType.class;
              }

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Role");
              }
            }

            @Order(20.0)
            @ClassId("f7534ab2-7635-47f5-89b5-9a3fcc669131")
            public class FromColumn extends AbstractDateColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("From");
              }
            }

            @Order(30.0)
            @ClassId("73765b19-c0a6-4aa5-88ae-0ca854fc512d")
            public class ToColumn extends AbstractDateColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("To");
              }
            }

            @Order(40.0)
            @ClassId("846be441-7523-49db-b287-190ecd4cda49")
            public class NotesColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Notes");
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }
            }

            @Order(10.0)
            @ClassId("4f0554f1-376d-4a70-b660-e871b73ca31a")
            public class NewRoleMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.Null, CalendarMenuType.EmptySpace, TableMenuType.EmptySpace, TreeMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewRole");
              }

              @Override
              protected void execAction() {
                RoleForm form = new RoleForm();
                form.getPersonField().setValue(getPersonNr());
                form.startGuiNew();
                form.waitFor();
                if (form.isFormStored()) {
                  ITableRow row = getRolesField().getTable().createRow();
                  getRolesField().getTable().getRoleColumn().setValue(row, form.getRoleField().getValue());
                  getRolesField().getTable().getFromColumn().setValue(row, form.getPeriodFrom().getValue());
                  getRolesField().getTable().getToColumn().setValue(row, form.getPeriodTo().getValue());
                  getRolesField().getTable().getNotesColumn().setValue(row, form.getNotesField().getValue());
                  getRolesField().getTable().addRow(row, true);
                }
              }
            }

            @Order(20.0)
            @ClassId("f5363be6-ea91-4c9f-a402-927db82d747e")
            public class EditRoleMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("EditRole");
              }

              @Override
              protected void execAction() {
                ITableRow row = getRolesField().getTable().getSelectedRow();
                RoleForm form = new RoleForm();
                form.getPersonField().setValue(getPersonNr());
                form.getRoleField().setValue(getRolesField().getTable().getRoleColumn().getValue(row));
                form.getPeriodFrom().setValue(getRolesField().getTable().getFromColumn().getValue(row));
                form.getPeriodTo().setValue(getRolesField().getTable().getToColumn().getValue(row));
                form.getNotesField().setValue(getRolesField().getTable().getNotesColumn().getValue(row));
                form.startGuiModify();
                form.waitFor();
                if (form.isFormStored()) {
                  getRolesField().getTable().getRoleColumn().setValue(row, form.getRoleField().getValue());
                  getRolesField().getTable().getFromColumn().setValue(row, form.getPeriodFrom().getValue());
                  getRolesField().getTable().getToColumn().setValue(row, form.getPeriodTo().getValue());
                  getRolesField().getTable().getNotesColumn().setValue(row, form.getNotesField().getValue());
                }
              }
            }

            @Order(30.0)
            @ClassId("062380b4-c570-4ba3-a16a-4d4a3fc73aa3")
            public class DeleteRoleMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("DeleteRole");
              }

              @Override
              protected void execAction() {
                getRolesField().getTable().deleteRow(getSelectedRow());
              }
            }
          }
        }
      }

      @Order(20.0)
      @ClassId("36b06ea1-4b53-4cab-abb9-ad00f01a482c")
      public class NotesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Order(10.0)
        @ClassId("4dacccf6-b3e6-41af-b1bc-4777f632cbd2")
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

    @Order(60.0)
    @ClassId("ee28a381-d917-4e98-b23a-3223e94a60d9")
    public class OkButton extends AbstractOkButton {
    }

    @Order(70.0)
    @ClassId("8eb02c76-d443-4101-b917-014dd56f2d83")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdatePersonPermission());
    }

    @Override
    public void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IPersonService service = BEANS.get(IPersonService.class);
      PersonFormData formData = new PersonFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}

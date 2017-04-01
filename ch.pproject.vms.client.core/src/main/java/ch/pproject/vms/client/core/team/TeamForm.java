package ch.pproject.vms.client.core.team;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.vms.client.core.team.TeamForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.DetailsBox;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.DetailsBox.LicenceTypeField;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.DetailsBox.NameField;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.MembersBox;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.MembersBox.MembersTableField;
import ch.pproject.vms.client.core.team.TeamForm.MainBox.OkButton;
import ch.pproject.vms.shared.core.person.PersonLookupCall;
import ch.pproject.vms.shared.core.team.ITeamService;
import ch.pproject.vms.shared.core.team.LicenceTypeCodeType;
import ch.pproject.vms.shared.core.team.TeamFormData;
import ch.pproject.vms.shared.core.team.UpdateTeamPermission;

@ClassId("4780f797-ec46-4dcf-bfd9-771e381bcb72")
@FormData(value = TeamFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TeamForm extends AbstractForm {

  private Long m_teamNr;

  public TeamForm() {
    super();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @FormData
  public Long getTeamNr() {
    return m_teamNr;
  }

  @FormData
  public void setTeamNr(Long teamNr) {
    m_teamNr = teamNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Team");
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

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public LicenceTypeField getLicenceTypeField() {
    return getFieldByClass(LicenceTypeField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MembersBox getMembersBox() {
    return getFieldByClass(MembersBox.class);
  }

  public MembersTableField getMembersTableField() {
    return getFieldByClass(MembersTableField.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  @ClassId("704b5f86-ace2-4e8e-9d98-93ec46d82756")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("bb812f48-4bed-4b96-9201-c393fbaae539")
    public class DetailsBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("a8fb1193-474a-4388-95d8-4290292e03ea")
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      @ClassId("244021bc-d154-4885-b365-bf740d2a587e")
      public class LicenceTypeField extends AbstractSmartField<Long> {

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return LicenceTypeCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("LicenceType");
        }
      }
    }

    @Order(15)
    public class MembersBox extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("TeamMembers");
      }

      @Order(1000)
      @ClassId("bb640ae4-91e3-49c4-b240-54a39ede8379")
      public class MembersTableField extends AbstractTableField<MembersTableField.Table> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @ClassId("9aa5cc60-8c56-4ce8-8d04-c7c234286985")
        public class Table extends AbstractTable {

          public PersonColumn getPersonColumn() {
            return getColumnSet().getColumnByClass(PersonColumn.class);
          }

          public ShirtNumberColumn getShirtNumberColumn() {
            return getColumnSet().getColumnByClass(ShirtNumberColumn.class);
          }

          @Order(10.0)
          @ClassId("2bd945ab-6f21-434b-95dd-4a364134fc37")
          public class PersonColumn extends AbstractSmartColumn<Long> {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Person");
            }

            @Override
            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
              return PersonLookupCall.class;
            }

            @Override
            protected int getConfiguredWidth() {
              return 400;
            }
          }

          @Order(20.0)
          @ClassId("a224a0a8-519f-45f7-989c-2c5206d9a7ce")
          public class ShirtNumberColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("ShirtNumber");
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }
          }

          @Order(110.0)
          @ClassId("16d1e6a7-2ffa-477d-a1c0-30e26b6005a6")
          public class NewTeamMemberMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("AddMember");
            }

            @Override
            protected void execAction() {
              TeamMemberForm form = new TeamMemberForm();
              form.setTeamNr(getTeamNr());
              form.startGuiNew();
              form.waitFor();
              if (form.isFormStored()) {
                ITableRow row = getMembersTableField().getTable().createRow();
                getMembersTableField().getTable().getPersonColumn().setValue(row, form.getPersonField().getValue());
                getMembersTableField().getTable().getShirtNumberColumn().setValue(row, form.getShirtNumberField().getValue());
                getMembersTableField().getTable().addRow(row, true);
              }
            }
          }

          @Order(120.0)
          @ClassId("9478d1c1-3547-48b7-a7bd-40fbadd9b29c")
          public class EditTeamMemberMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("EditMember");
            }

            @Override
            protected void execAction() {
              ITableRow row = getMembersTableField().getTable().getSelectedRow();
              TeamMemberForm form = new TeamMemberForm();
              form.setTeamNr(getTeamNr());
              form.getPersonField().setValue(getMembersTableField().getTable().getPersonColumn().getValue(row));
              form.getShirtNumberField().setValue(getMembersTableField().getTable().getShirtNumberColumn().getValue(row));
              form.startGuiModify();
              form.waitFor();
              if (form.isFormStored()) {
                getMembersTableField().getTable().getPersonColumn().setValue(row, form.getPersonField().getValue());
                getMembersTableField().getTable().getShirtNumberColumn().setValue(row, form.getShirtNumberField().getValue());
              }
            }
          }

          @Order(140.0)
          @ClassId("d38db1ab-9f5b-46f8-94c9-ef215cedfacd")
          public class RemoveTeamMemberMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("RemoveMember");
            }

            @Override
            protected void execSelectionChanged(boolean selection) {
              if (getSelectedRowCount() > 1) {
                setText(TEXTS.get("RemoveMembers"));
              }
              else {
                setText(getConfiguredText());
              }
            }

            @Override
            protected void execAction() {
              deleteRows(getSelectedRows());
            }
          }
        }
      }
    }

    @Order(20.0)
    @ClassId("9e178c39-4ff1-4a0d-9129-c5b0b673edde")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("c4fef658-6d2c-4d2d-8d1e-9a361175f0db")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamFormData formData = new TeamFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateTeamPermission());

    }

    @Override
    protected void execStore() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamFormData formData = new TeamFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamFormData formData = new TeamFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

    }

    @Override
    protected void execStore() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamFormData formData = new TeamFormData();
      exportFormData(formData);
      formData = service.create(formData);

    }
  }
}

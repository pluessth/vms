package ch.pproject.vms.client.core.person;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import ch.pproject.vms.client.core.person.RoleForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.NotesField;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.PeriodBox;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.PeriodBox.PeriodFrom;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.PeriodBox.PeriodTo;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.PersonField;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.HeaderBox.RoleField;
import ch.pproject.vms.client.core.person.RoleForm.MainBox.OkButton;
import ch.pproject.vms.shared.core.person.IRoleService;
import ch.pproject.vms.shared.core.person.PersonLookupCall;
import ch.pproject.vms.shared.core.person.RoleFormData;
import ch.pproject.vms.shared.core.person.UpdateRolePermission;
import ch.pproject.vms.shared.core.services.code.RoleCodeType;

@ClassId("2930a1d6-4d98-4cb7-a923-f59ce822b05e")
@FormData(value = RoleFormData.class, sdkCommand = SdkCommand.CREATE)
public class RoleForm extends AbstractForm {

  private Long m_roleNr;

  public RoleForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Role");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public void startGuiNew() {
    startInternal(new GuiNewHandler());
  }

  public void startGuiModify() {
    startInternal(new GuiModifyHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PeriodBox getPeriodBox() {
    return getFieldByClass(PeriodBox.class);
  }

  public PeriodFrom getPeriodFrom() {
    return getFieldByClass(PeriodFrom.class);
  }

  public PeriodTo getPeriodTo() {
    return getFieldByClass(PeriodTo.class);
  }

  public PersonField getPersonField() {
    return getFieldByClass(PersonField.class);
  }

  public RoleField getRoleField() {
    return getFieldByClass(RoleField.class);
  }

  @Order(10.0)
  @ClassId("6901e232-aca1-4782-b10e-6a23f524c8c4")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("1817a25d-789f-4e2a-a8a1-aac1d59ab5a4")
    public class HeaderBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      @ClassId("20b6e442-0946-4d44-b2ee-78e19dab1547")
      public class PersonField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Person");
        }

        @Override
        protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
          return PersonLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      @ClassId("2b920092-af7d-4157-9327-1b1c7cba27eb")
      public class RoleField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Role");
        }

        @Override
        protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
          return RoleCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(30.0)
      @ClassId("8d6d7409-994d-401b-9668-9d40eb8a7df4")
      public class PeriodBox extends AbstractSequenceBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Period");
        }

        @Order(10.0)
        @ClassId("60312fe6-a7a5-45b7-883c-a7d7a81c1eb0")
        public class PeriodFrom extends AbstractDateField {

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
        @ClassId("47df9855-10f5-4102-8749-6f3b971d5413")
        public class PeriodTo extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("to");
          }
        }
      }

      @Order(40.0)
      @ClassId("53e3e0aa-6cca-40df-ada7-305ce0fa12ef")
      public class NotesField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 100;
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

    @Order(50.0)
    @ClassId("b0029848-5c08-461d-ae7f-2d80558128f2")
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
    @ClassId("e366d94f-5ff1-4f1b-b9ec-9a5aa5d9a842")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRoleService service = BEANS.get(IRoleService.class);
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateRolePermission());
    }

    @Override
    public void execStore() {
      IRoleService service = BEANS.get(IRoleService.class);
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRoleService service = BEANS.get(IRoleService.class);
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRoleService service = BEANS.get(IRoleService.class);
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }

  public class GuiNewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      getPersonField().setView(false, false, false);
    }

    @Override
    public void execStore() {
    }
  }

  public class GuiModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      getPeriodFrom().setView(true, false, false);
      getRoleField().setView(true, false, false);
      getPersonField().setView(false, false, false);
    }

    @Override
    public void execStore() {
    }
  }

  @FormData
  public Long getRoleNr() {
    return m_roleNr;
  }

  @FormData
  public void setRoleNr(Long roleNr) {
    m_roleNr = roleNr;
  }
}

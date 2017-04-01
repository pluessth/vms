package ch.pproject.vms.client.core.team;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.vms.client.core.team.TeamMemberForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.team.TeamMemberForm.MainBox.GroupBox;
import ch.pproject.vms.client.core.team.TeamMemberForm.MainBox.GroupBox.PersonField;
import ch.pproject.vms.client.core.team.TeamMemberForm.MainBox.GroupBox.ShirtNumberField;
import ch.pproject.vms.client.core.team.TeamMemberForm.MainBox.OkButton;
import ch.pproject.vms.shared.core.person.PersonLookupCall;
import ch.pproject.vms.shared.core.team.ITeamService;
import ch.pproject.vms.shared.core.team.TeamMemberFormData;
import ch.pproject.vms.shared.core.team.UpdateTeamPermission;

@ClassId("04ccdee3-fc0d-4482-b1ee-73969c6932a1")
@FormData(value = TeamMemberFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TeamMemberForm extends AbstractForm {

  private Long m_teamNr;

  public TeamMemberForm() {
    super();
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
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Teammitglied");
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public void startGuiModify() {
    startInternal(new GuiModifyHandler());
  }

  public void startGuiNew() {
    startInternal(new GuiNewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PersonField getPersonField() {
    return getFieldByClass(PersonField.class);
  }

  public ShirtNumberField getShirtNumberField() {
    return getFieldByClass(ShirtNumberField.class);
  }

  @Order(10.0)
  @ClassId("e390897b-2e57-4945-88b9-7771a9afde6d")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("0cd978a2-f457-441d-b511-11089df38056")
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("9ec8f2b8-dd69-4760-bd0b-342488e45d8a")
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
      @ClassId("bd38fd22-6cbe-446f-bcdb-154ea718a076")
      public class ShirtNumberField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ShirtNumber");
        }
      }
    }

    @Order(20.0)
    @ClassId("f260b5c8-0846-4017-adcb-48827df8dd43")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("fc651c40-45e5-49fa-882c-c64e51771bd9")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamMemberFormData formData = new TeamMemberFormData();
      exportFormData(formData);
      formData = service.loadTeamMember(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateTeamPermission());
    }

    @Override
    protected void execPostLoad() {
      getPersonField().setEnabled(false);
    }

    @Override
    protected void execStore() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamMemberFormData formData = new TeamMemberFormData();
      exportFormData(formData);
      formData = service.storeTeamMember(formData);
    }

  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      ITeamService service = BEANS.get(ITeamService.class);
      TeamMemberFormData formData = new TeamMemberFormData();
      exportFormData(formData);
      formData = service.createTeamMember(formData);

    }

  }

  public class GuiNewHandler extends AbstractFormHandler {

  }

  public class GuiModifyHandler extends AbstractFormHandler {

  }

}

package ch.pproject.vms.client.core.administration;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.vms.client.core.administration.UserForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.administration.UserForm.MainBox.GroupBox;
import ch.pproject.vms.client.core.administration.UserForm.MainBox.GroupBox.PasswordField;
import ch.pproject.vms.client.core.administration.UserForm.MainBox.GroupBox.PermissionsBox;
import ch.pproject.vms.client.core.administration.UserForm.MainBox.GroupBox.UserNameField;
import ch.pproject.vms.client.core.administration.UserForm.MainBox.OkButton;
import ch.pproject.vms.shared.core.administration.CreateUserPermission;
import ch.pproject.vms.shared.core.administration.IUserService;
import ch.pproject.vms.shared.core.administration.PermissionLookupCall;
import ch.pproject.vms.shared.core.administration.UpdateUserPermission;
import ch.pproject.vms.shared.core.administration.UserFormData;
import ch.pproject.vms.shared.core.security.UserUtility;

@FormData(value = UserFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserForm extends AbstractForm {

  private Long m_userNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  @FormData
  public Long getUserNr() {
    return m_userNr;
  }

  @FormData
  public void setUserNr(Long userNr) {
    m_userNr = userNr;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public UserNameField getUserNameField() {
    return getFieldByClass(UserNameField.class);
  }

  public PermissionsBox getPermissionsBox() {
    return getFieldByClass(PermissionsBox.class);
  }

  public PasswordField getPasswordField() {
    return getFieldByClass(PasswordField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(1000)
      public class UserNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Username");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 20;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if (!isFormLoading() && StringUtility.hasText(rawValue) && !rawValue.matches(UserUtility.USERNAME_REGEX)) {
            throw new VetoException(TEXTS.get("UserNameInvalidMessage"));
          }
          return rawValue;
        }

      }

      @Order(1500)
      public class PasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Password");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 20;
        }

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }
      }

      @Order(2000)
      public class PermissionsBox extends AbstractListBox<Class> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Permissions");
        }

        @Override
        protected int getConfiguredGridH() {
          return 10;
        }

        @Override
        protected Class<? extends ILookupCall<Class>> getConfiguredLookupCall() {
          return PermissionLookupCall.class;
        }
      }

    }

    @Order(100000)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      IUserService service = BEANS.get(IUserService.class);
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateUserPermission());
    }

    @Override
    protected void execPostLoad() {
      getPasswordField().setVisibleGranted(false);
      getPasswordField().setView(false, false, false);
      getUserNameField().setView(true, false, false);
    }

    @Override
    protected void execStore() {
      IUserService service = BEANS.get(IUserService.class);
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      IUserService service = BEANS.get(IUserService.class);
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateUserPermission());
    }

    @Override
    protected void execPostLoad() {
      getPasswordField().setView(true, true, true);
      getUserNameField().setView(true, true, true);
    }

    @Override
    protected void execStore() {
      IUserService service = BEANS.get(IUserService.class);
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      service.create(formData);
    }
  }
}

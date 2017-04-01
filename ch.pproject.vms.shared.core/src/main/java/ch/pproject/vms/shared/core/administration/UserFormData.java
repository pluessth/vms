package ch.pproject.vms.shared.core.administration;

import java.util.Set;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.vms.client.core.administration.UserForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class UserFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public Password getPassword() {
    return getFieldByClass(Password.class);
  }

  public PermissionsBox getPermissionsBox() {
    return getFieldByClass(PermissionsBox.class);
  }

  public UserName getUserName() {
    return getFieldByClass(UserName.class);
  }

  /**
   * access method for property UserNr.
   */
  public Long getUserNr() {
    return getUserNrProperty().getValue();
  }

  /**
   * access method for property UserNr.
   */
  public void setUserNr(Long userNr) {
    getUserNrProperty().setValue(userNr);
  }

  public UserNrProperty getUserNrProperty() {
    return getPropertyByClass(UserNrProperty.class);
  }

  public static class Password extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class PermissionsBox extends AbstractValueFieldData<Set<Class>> {

    private static final long serialVersionUID = 1L;
  }

  public static class UserName extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class UserNrProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }
}
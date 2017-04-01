package ch.pproject.vms.shared.core.person;

import java.util.Date;
import java.util.Set;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@ClassId("4e3e3779-e13d-44d2-ba9b-93d62eef3cd7-formdata")
@Generated(value = "ch.pproject.vms.client.core.person.PersonSearchForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class PersonSearchFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public DateOfBirthFrom getDateOfBirthFrom() {
    return getFieldByClass(DateOfBirthFrom.class);
  }

  public DateOfBirthTo getDateOfBirthTo() {
    return getFieldByClass(DateOfBirthTo.class);
  }

  public Name getName() {
    return getFieldByClass(Name.class);
  }

  public Phone getPhone() {
    return getFieldByClass(Phone.class);
  }

  /**
   * access method for property RoleUid.
   */
  public Long getRoleUid() {
    return getRoleUidProperty().getValue();
  }

  /**
   * access method for property RoleUid.
   */
  public void setRoleUid(Long roleUid) {
    getRoleUidProperty().setValue(roleUid);
  }

  public RoleUidProperty getRoleUidProperty() {
    return getPropertyByClass(RoleUidProperty.class);
  }

  public Sex getSex() {
    return getFieldByClass(Sex.class);
  }

  @ClassId("a0d27297-dbe5-425b-b268-133985feb3f0-formdata")
  public static class DateOfBirthFrom extends AbstractValueFieldData<Date> {

    private static final long serialVersionUID = 1L;
  }

  @ClassId("e0c70076-1385-46fc-882f-7c6854c403dd-formdata")
  public static class DateOfBirthTo extends AbstractValueFieldData<Date> {

    private static final long serialVersionUID = 1L;
  }

  @ClassId("828f1062-0818-4519-9a00-6a627ea1ad8e-formdata")
  public static class Name extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  @ClassId("1c929b59-9f30-4d9a-967a-2bd4c4d1397d-formdata")
  public static class Phone extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class RoleUidProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }

  @ClassId("b85acc5c-2d5a-4b11-9dbf-2222d1d39795-formdata")
  public static class Sex extends AbstractValueFieldData<Set<Long>> {

    private static final long serialVersionUID = 1L;
  }
}
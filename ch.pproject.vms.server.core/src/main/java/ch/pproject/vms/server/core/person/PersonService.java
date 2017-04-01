package ch.pproject.vms.server.core.person;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.core.person.CreatePersonPermission;
import ch.pproject.vms.shared.core.person.IPersonService;
import ch.pproject.vms.shared.core.person.IRoleService;
import ch.pproject.vms.shared.core.person.PersonFormData;
import ch.pproject.vms.shared.core.person.PersonFormData.Roles.RolesRowData;
import ch.pproject.vms.shared.core.person.ReadPersonPermission;
import ch.pproject.vms.shared.core.person.RoleFormData;
import ch.pproject.vms.shared.core.person.UpdatePersonPermission;

public class PersonService implements IPersonService {

  @Override
  public PersonFormData prepareCreate(PersonFormData formData) {
    if (!ACCESS.check(new CreatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public PersonFormData create(PersonFormData formData) {
    if (!ACCESS.check(new CreatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData.setPersonNr(SQL.getSequenceNextval(ISequences.VMSSEQ));
    SQL.insert("" +
        "INSERT INTO PERSON (" +
        " PERSON_NR, " +
        " NAME, " +
        " FIRSTNAME, " +
        " SEX, " +
        " DATEOFBIRTH, " +
        " STREET, " +
        " ZIP, " +
        " CITY, " +
        " PHONEPRIVATE, " +
        " PHONEWORK, " +
        " PHONEMOBILE, " +
        " EMAIL, " +
        " PLAYER_LICENSE_NO, " +
        " REFEREE_LICENSE_NO, " +
        " NOTES" +
        ") VALUES (" +
        " :personNr, " +
        " :name, " +
        " :firstName, " +
        " :sex, " +
        " :dateOfBirth, " +
        " :street, " +
        " :zip, " +
        " :city, " +
        " :phonePrivate, " +
        " :phoneWork, " +
        " :phoneMobile, " +
        " :eMailAddress, " +
        " :playerLicenseNumber, " +
        " :refereeLicenseNumber, " +
        " :notes ) ", formData);
    storeRoles(formData);
    return formData;
  }

  @Override
  public PersonFormData load(PersonFormData formData) {
    if (!ACCESS.check(new ReadPersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT " +
        " NAME, " +
        " FIRSTNAME, " +
        " SEX, " +
        " DATEOFBIRTH, " +
        " STREET, " +
        " ZIP, " +
        " CITY, " +
        " PHONEPRIVATE, " +
        " PHONEWORK, " +
        " PHONEMOBILE, " +
        " EMAIL, " +
        " PLAYER_LICENSE_NO, " +
        " REFEREE_LICENSE_NO, " +
        " NOTES " +
        "FROM PERSON " +
        "WHERE PERSON_NR = :personNr " +
        "INTO :name, " +
        " :firstName, " +
        " :sex, " +
        " :dateOfBirth, " +
        " :street, " +
        " :zip, " +
        " :city, " +
        " :phonePrivate, " +
        " :phoneWork, " +
        " :phoneMobile, " +
        " :eMailAddress, " +
        " :playerLicenseNumber, " +
        " :refereeLicenseNumber, " +
        " :notes ", formData);
    loadRoles(formData);
    return formData;
  }

  private PersonFormData loadRoles(PersonFormData formData) {
    SQL.selectInto("" +
        "SELECT " +
        " ROLE_UID, " +
        " FROM_DATE, " +
        " TO_DATE, " +
        " NOTES " +
        "FROM ROLE " +
        "WHERE PERSON_NR = :personNr " +
        "INTO " +
        " :{role}, " +
        " :{from}, " +
        " :{to}, " +
        " :{notes} ", formData.getRoles(), formData);
    return formData;
  }

  @Override
  public PersonFormData store(PersonFormData formData) {
    if (!ACCESS.check(new UpdatePersonPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.update("UPDATE PERSON " +
        "SET " +
        " NAME = :name, " +
        " FIRSTNAME = :firstName, " +
        " SEX = :sex, " +
        " DATEOFBIRTH = :dateOfBirth, " +
        " STREET = :street, " +
        " ZIP = :zip, " +
        " CITY = :city, " +
        " PHONEPRIVATE = :phonePrivate, " +
        " PHONEWORK = :phoneWork, " +
        " PHONEMOBILE = :phoneMobile, " +
        " EMAIL = :eMailAddress, " +
        " PLAYER_LICENSE_NO = :playerLicenseNumber, " +
        " REFEREE_LICENSE_NO = :refereeLicenseNumber, " +
        " NOTES = :notes " +
        "WHERE  PERSON_NR = :personNr ", formData);
    storeRoles(formData);
    return formData;
  }

  private PersonFormData storeRoles(PersonFormData formData) {
    IRoleService svc = BEANS.get(IRoleService.class);
    for (int i = 0; i < formData.getRoles().getRowCount(); i++) {
      RoleFormData role = new RoleFormData();
      RolesRowData row = formData.getRoles().rowAt(i);
      role.getRole().setValue(row.getRole());
      role.getPerson().setValue(formData.getPersonNr());
      role.getPeriodFrom().setValue(row.getFrom());
      role.getPeriodTo().setValue(row.getTo());
      if (row.getRowState() == ITableBeanRowHolder.STATUS_INSERTED) {
        role = svc.create(role);
      }
      else if (row.getRowState() == ITableBeanRowHolder.STATUS_UPDATED) {
        role = svc.store(role);
      }
      else if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED) {
        role = svc.delete(role);
      }
    }
    return formData;
  }

  @Override
  public void storeSilent(AbstractFormData formData) {
    if (formData == null) {
      return;
    }
    if (formData instanceof PersonFormData) {
      create((PersonFormData) formData);
    }
    else {
      throw new ProcessingException("Not possible to call " + getClass().getName() + ".storeSilent(AbstractFormData)' with an formData of type " + formData.getClass());
    }
  }
}

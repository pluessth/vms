package ch.pproject.vms.server.core.person;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.core.person.CreateRolePermission;
import ch.pproject.vms.shared.core.person.DeleteRolePermission;
import ch.pproject.vms.shared.core.person.IRoleService;
import ch.pproject.vms.shared.core.person.ReadRolePermission;
import ch.pproject.vms.shared.core.person.RoleFormData;
import ch.pproject.vms.shared.core.person.UpdateRolePermission;

public class RoleService implements IRoleService {

  @Override
  public RoleFormData prepareCreate(RoleFormData formData) {
    if (!ACCESS.check(new CreateRolePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public RoleFormData create(RoleFormData formData) {
    if (!ACCESS.check(new CreateRolePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData.setRoleNr(SQL.getSequenceNextval(ISequences.VMSSEQ));
    SQL.insert("" +
        "INSERT INTO ROLE (" +
        " ROLE_NR, " +
        " PERSON_NR, " +
        " ROLE_UID, " +
        " FROM_DATE, " +
        " TO_DATE," +
        " NOTES " +
        ") VALUES (" +
        " :roleNr, " +
        " :person, " +
        " :role, " +
        " :periodFrom, " +
        " :periodTo," +
        " :notes " +
        ") ",
        formData);
    return formData;
  }

  @Override
  public RoleFormData load(RoleFormData formData) {
    if (!ACCESS.check(new ReadRolePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT " +
        " PERSON_NR, " +
        " ROLE_UID, " +
        " FROM_DATE, " +
        " TO_DATE, " +
        " NOTES " +
        "FROM ROLE " +
        "WHERE ROLE_NR = :roleNr " +
        "INTO " +
        " :person, " +
        " :role, " +
        " :periodFrom, " +
        " :periodTo, " +
        " :notes ",
        formData);
    return formData;
  }

  @Override
  public RoleFormData store(RoleFormData formData) {
    if (!ACCESS.check(new UpdateRolePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.update("" +
        "UPDATE ROLE SET " +
        " NOTES = :notes, " +
        "TO_DATE = :periodTo " +
        "WHERE  ROLE_NR = :roleNr ",
        formData);
    return formData;
  }

  @Override
  public RoleFormData delete(RoleFormData formData) {
    if (!ACCESS.check(new DeleteRolePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.update("" +
        "DELETE FROM ROLE " +
        "WHERE ROLE_NR = :roleNr ",
        formData);
    return formData;
  }
}

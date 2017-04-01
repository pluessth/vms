package ch.pproject.vms.server.core.team;

import java.util.List;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.core.team.CreateTeamPermission;
import ch.pproject.vms.shared.core.team.ITeamService;
import ch.pproject.vms.shared.core.team.ReadTeamPermission;
import ch.pproject.vms.shared.core.team.TeamFormData;
import ch.pproject.vms.shared.core.team.TeamFormData.MembersTable.MembersTableRowData;
import ch.pproject.vms.shared.core.team.TeamMemberFormData;
import ch.pproject.vms.shared.core.team.UpdateTeamPermission;

public class TeamService implements ITeamService {

  @Override
  public TeamFormData prepareCreate(TeamFormData formData) {
    if (!ACCESS.check(new CreateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public TeamFormData create(TeamFormData formData) {
    if (!ACCESS.check(new CreateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (!isNameUnique(formData.getTeamNr(), formData.getName().getValue())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    formData.setTeamNr(SQL.getSequenceNextval(ISequences.VMSSEQ));
    SQL.insert("" +
        "INSERT INTO TEAM (" +
        " TEAM_NR, " +
        " NAME, " +
        " LICENCE_TYPE_UID " +
        ") VALUES (" +
        " :teamNr, " +
        " :name, " +
        " :licenceType " +
        ") ", formData);

    return formData;
  }

  private boolean isNameUnique(Long teamNr, String name) {
    LongHolder exists = new LongHolder();
    SQL.selectInto("" +
        "SELECT COUNT(1) " +
        "FROM   TEAM " +
        "WHERE  (TEAM_NR <> :teamNr OR :teamNr IS NULL) " +
        "AND    NAME = :name " +
        "INTO   :exists ",
        new NVPair("teamNr", teamNr), new NVPair("exists", exists), new NVPair("name", name));
    return exists.getValue() == 0;
  }

  @Override
  public TeamFormData load(TeamFormData formData) {
    if (!ACCESS.check(new ReadTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT " +
        " NAME, " +
        " LICENCE_TYPE_UID " +
        "FROM TEAM " +
        "WHERE TEAM_NR = :teamNr " +
        "INTO " +
        " :name, " +
        " :licenceType ", formData);

    formData = loadTeamMembers(formData);
    return formData;
  }

  @Override
  public TeamFormData store(TeamFormData formData) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (!isNameUnique(formData.getTeamNr(), formData.getName().getValue())) {
      throw new VetoException(TEXTS.get("ThisTeamNameAlreadyExists"));
    }

    SQL.update("" +
        "UPDATE TEAM SET " +
        " NAME = :name, " +
        " LICENCE_TYPE_UID = :licenceType " +
        "WHERE TEAM_NR = :teamNr ",
        formData);

    formData = storeTeamMembers(formData);
    return formData;
  }

  @Override
  public TeamMemberFormData createTeamMember(TeamMemberFormData formData) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (teamMemberExists(formData.getTeamNr(), formData.getPerson().getValue())) {
      throw new VetoException(TEXTS.get("ThePersonIsAlreadyAssignedToTheTeam"));
    }

    SQL.insert("" +
        "INSERT INTO TEAM_MEMBER (" +
        " TEAM_NR, " +
        " PERSON_NR, " +
        " SHIRT_NUMBER " +
        ") VALUES (" +
        " :teamNr, " +
        " :person, " +
        " :shirtNumber " +
        ") ", formData);
    return formData;
  }

  @Override
  public TeamMemberFormData storeTeamMember(TeamMemberFormData formData) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("" +
        "UPDATE TEAM_MEMBER SET " +
        " SHIRT_NUMBER = :shirtNumber " +
        "WHERE TEAM_NR = :teamNr " +
        "AND PERSON_NR = :person ", formData);
    return formData;
  }

  @Override
  public TeamMemberFormData deleteTeamMember(TeamMemberFormData formData) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.delete("" +
        "DELETE FROM TEAM_MEMBER " +
        "WHERE TEAM_NR = :teamNr " +
        "AND PERSON_NR = :person", formData);
    return formData;
  }

  @Override
  public TeamMemberFormData loadTeamMember(TeamMemberFormData formData) {
    if (!ACCESS.check(new ReadTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT " +
        " SHIRT_NUMBER " +
        "FROM TEAM_MEMBER " +
        "WHERE TEAM_NR = :teamNr " +
        "AND PERSON_NR = :person " +
        "INTO :shirtNumber ", formData);
    return formData;
  }

  private TeamFormData loadTeamMembers(TeamFormData formData) {
    SQL.selectInto("" +
        "SELECT PERSON_NR, " +
        " SHIRT_NUMBER " +
        "FROM TEAM_MEMBER " +
        "WHERE TEAM_NR = :teamNr " +
        "INTO " +
        " :{person}, " +
        " :{shirtNumber} ", formData.getMembersTable(), formData);
    return formData;
  }

  private TeamFormData storeTeamMembers(TeamFormData formData) {
    for (int i = 0; i < formData.getMembersTable().getRowCount(); i++) {
      TeamMemberFormData teamMember = new TeamMemberFormData();
      MembersTableRowData row = formData.getMembersTable().rowAt(i);
      teamMember.setTeamNr(formData.getTeamNr());
      teamMember.getPerson().setValue(row.getPerson());
      teamMember.getShirtNumber().setValue(row.getShirtNumber());
      if (row.getRowState() == ITableBeanRowHolder.STATUS_INSERTED) {
        teamMember = createTeamMember(teamMember);
      }
      else if (row.getRowState() == ITableBeanRowHolder.STATUS_UPDATED) {
        teamMember = storeTeamMember(teamMember);
      }
      else if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED) {
        teamMember = deleteTeamMember(teamMember);
      }
    }
    return formData;
  }

  @Override
  public void removePersonsFromTeam(Long teamNr, List<Long> personNrs) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.delete("" +
        "DELETE FROM TEAM_MEMBER " +
        "WHERE TEAM_NR = :teamNr " +
        "AND PERSON_NR in (:{personNrs})",
        new NVPair("teamNr", teamNr), new NVPair("personNrs", personNrs));

  }

  private boolean teamMemberExists(Long teamNr, Long personNr) {
    LongHolder exists = new LongHolder();
    SQL.selectInto("" +
        "SELECT COUNT(1) " +
        "FROM   TEAM_MEMBER " +
        "WHERE  TEAM_NR = :teamNr " +
        "AND    PERSON_NR = :personNr " +
        "INTO   :exists ",
        new NVPair("teamNr", teamNr), new NVPair("exists", exists), new NVPair("personNr", personNr));
    return exists.getValue() > 0;
  }

  @Override
  public void deleteTeam(Long teamNr) {
    if (!ACCESS.check(new UpdateTeamPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.delete("" +
        "DELETE FROM TEAM " +
        "WHERE TEAM_NR = :teamNr ",
        new NVPair("teamNr", teamNr));
  }
}

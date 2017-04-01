package ch.pproject.vms.server.core.services;

import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.person.ActiveMemberTablePageData;
import ch.pproject.vms.shared.core.person.AllPersonTablePageData;
import ch.pproject.vms.shared.core.person.PersonSearchFormData;
import ch.pproject.vms.shared.core.services.IMemberOutlineService;
import ch.pproject.vms.shared.core.team.TeamTablePageData;

public class MemberOutlineService implements IMemberOutlineService {

  public static StringBuilder getPersonBaseQuery(PersonSearchFormData formData) {
    StringBuilder statement = new StringBuilder();
    statement.append("" +
        "SELECT " +
        " P.PERSON_NR, " +
        " P.NAME, " +
        " P.FIRSTNAME, " +
        " P.SEX, " +
        " P.DATEOFBIRTH, " +
        " P.STREET, " +
        " P.ZIP, " +
        " P.CITY, " +
        " P.PHONEPRIVATE, " +
        " P.PHONEWORK, " +
        " P.PHONEMOBILE, " +
        " P.EMAIL " +
        " FROM PERSON P " +
        " WHERE 1 = 1 ");

    if (formData != null) {
      if (StringUtility.hasText(formData.getName().getValue())) {
        statement.append(
            "AND (UPPER(CONCAT(COALESCE(P.FIRSTNAME, ''), ' ', COALESCE(P.NAME, ''))) LIKE UPPER(CONCAT('%',:name,'%')) " +
                " OR UPPER(CONCAT(COALESCE(P.NAME, ''), ' ', COALESCE(P.FIRSTNAME, ''))) LIKE UPPER(CONCAT('%',:name,'%'))) ");
      }
      if (formData.getDateOfBirthFrom().getValue() != null) {
        statement.append("AND P.DATEOFBIRTH >= :dateOfBirthFrom ");
      }
      if (formData.getDateOfBirthTo().getValue() != null) {
        statement.append("AND P.DATEOFBIRTH <= :dateOfBirthTo ");
      }
      if (formData.getSex().getValue() != null) {
        statement.append("AND P.SEX = :sex ");
      }
      if (!StringUtility.isNullOrEmpty(formData.getPhone().getValue())) {
        statement.append("AND UPPER(CONCAT(COALESCE(P.PHONEPRIVATE, ''), ' ', COALESCE(P.PHONEWORK, ''), ' ', COALESCE(P.PHONEMOBILE, ''))) LIKE UPPER(CONCAT('%',:phone,'%')) ");
      }
    }
    return statement;
  }

  public static final String PERSON_INTO_CLAUSE = "" +
      "INTO :{page.id}, " +
      " :{page.name}, " +
      " :{page.firstName}, " +
      " :{page.sex}, " +
      " :{page.dateOfBirth}, " +
      " :{page.street}, " +
      " :{page.zip}, " +
      " :{page.city}, " +
      " :{page.phonePrivate}, " +
      " :{page.phoneWork}, " +
      " :{page.phoneMobile}, " +
      " :{page.eMailAddress} ";

  @Override
  public AbstractTablePageData getAllPersonsTableData(SearchFilter filter) {
    PersonSearchFormData formData = (PersonSearchFormData) filter.getFormData();
    StringBuilder statement = getPersonBaseQuery(formData);
    AllPersonTablePageData page = new AllPersonTablePageData();
    SQL.selectInto(statement.append(PERSON_INTO_CLAUSE).toString(), formData, new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getMembersTableData(SearchFilter filter) {
    PersonSearchFormData formData = (PersonSearchFormData) filter.getFormData();
    StringBuilder statement = getPersonBaseQuery(formData);
    statement.append(" AND EXISTS (SELECT 1 FROM ROLE R WHERE P.PERSON_NR = R.PERSON_NR AND R.ROLE_UID = :roleUid) ");
    ActiveMemberTablePageData page = new ActiveMemberTablePageData();
    SQL.selectInto(statement.append(PERSON_INTO_CLAUSE).toString(), formData, new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getTeamTableData(SearchFilter filter) {
    TeamTablePageData page = new TeamTablePageData();
    SQL.selectInto("" +
        "SELECT T.TEAM_NR, " +
        " T.NAME, " +
        " T.LICENCE_TYPE_UID, " +
        " GROUP_CONCAT(CONCAT(P.FIRSTNAME, ' ', P.NAME, COALESCE(CONCAT(' (', TM.SHIRT_NUMBER, ')'), '')) ORDER BY CONCAT(P.FIRSTNAME, ' ', P.NAME, COALESCE(CONCAT(' (', TM.SHIRT_NUMBER, ')'), '')) separator '\r\n') " +
        "FROM TEAM T " +
        "LEFT OUTER JOIN TEAM_MEMBER TM ON T.TEAM_NR = TM.TEAM_NR " +
        "LEFT OUTER JOIN PERSON P ON P.PERSON_NR = TM.PERSON_NR " +
        "GROUP BY T.TEAM_NR, T.NAME, T.LICENCE_TYPE_UID " +
        "INTO :{page.teamNr}, " +
        " :{page.name}, " +
        " :{page.licenceType}, " +
        " :{page.teamMembers} ", new NVPair("page", page));
    return page;
  }

}

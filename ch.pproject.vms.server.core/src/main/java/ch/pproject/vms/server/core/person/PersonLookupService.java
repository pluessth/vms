package ch.pproject.vms.server.core.person;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import ch.pproject.vms.shared.core.person.IPersonLookupService;

public class PersonLookupService extends AbstractSqlLookupService<Long> implements IPersonLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return ""
        + "SELECT "
        + " PERSON_NR, "
        + " CONCAT(FIRSTNAME, ' ', NAME) "
        + "FROM PERSON "
        + "WHERE 1=1 "
        + "<key>AND PERSON_NR = :key </key> "
        + "<text>AND NAME LIKE :text </text> ";
  }
}

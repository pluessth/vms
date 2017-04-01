package ch.pproject.vms.server.core.administration;

import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.administration.UserPermissionTablePageData;
import ch.pproject.vms.shared.core.administration.UserTablePageData;
import ch.pproject.vms.shared.core.services.IAdministrationOutlineService;

public class AdministrationOutlineService implements IAdministrationOutlineService {

  @Override
  public AbstractTablePageData getUserTableData(SearchFilter filter) {
    UserTablePageData page = new UserTablePageData();
    SQL.selectInto("" +
        "SELECT " +
        " USER_NR, " +
        " USERNAME " +
        "FROM USER " +
        "INTO :{page.userNr}, " +
        " :{page.username} ",
        new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getUserPermissionTableData(Long userNr, SearchFilter filter) {
    UserPermissionTablePageData page = new UserPermissionTablePageData();
    SQL.selectInto("" +
        "SELECT  " +
        " USER_NR, " +
        " PERMISSION_NAME " +
        "FROM USER_PERMISSION " +
        "WHERE USER_NR = :userNr " +
        "INTO :{page.userNr}, " +
        " :{page.permissionName} ",
        new NVPair("userNr", userNr),
        new NVPair("page", page));
    return page;
  }

}

package ch.pproject.vms.client.core.administration;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.administration.UserPermissionTablePageData;
import ch.pproject.vms.shared.core.services.IAdministrationOutlineService;

import org.eclipse.scout.rt.platform.classid.ClassId;

@PageData(UserPermissionTablePageData.class)
@ClassId("df5ce46e-72a8-46dd-b03e-dee4411600aa")
public class UserPermissionTablePage extends AbstractPageWithTable<UserPermissionTablePage.Table> {

  private final Long m_userNr;

  public UserPermissionTablePage(Long userNr) {
    m_userNr = userNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Permissions");
  }

  public Long getUserNr() {
    return m_userNr;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAdministrationOutlineService.class).getUserPermissionTableData(getUserNr(), filter));
  }

  @Order(10.0)
  @ClassId("13544eb5-8b36-4def-9e0c-f651f78a2fad")
  public class Table extends AbstractTable {

    public PermissionNameColumn getPermissionNameColumn() {
      return getColumnSet().getColumnByClass(PermissionNameColumn.class);
    }

    public UserNrColumn getUserNrColumn() {
      return getColumnSet().getColumnByClass(UserNrColumn.class);
    }

    @Order(1000)
    @ClassId("363d140c-d696-4bed-b08f-1ee74ebd85c2")
    public class UserNrColumn extends AbstractLongColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    @ClassId("2d07106c-f448-41bd-9638-723174c84a7a")
    public class PermissionNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

  }

}

package ch.pproject.vms.client.core.administration;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.administration.UserTablePageData;
import ch.pproject.vms.shared.core.services.IAdministrationOutlineService;

@PageData(UserTablePageData.class)
@ClassId("0c0f5319-1bf2-4f0b-a100-16569a8ad7a5")
public class UserTablePage extends AbstractPageWithTable<UserTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Users");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new UserPermissionTablePage(getTable().getUserNrColumn().getValue(row));
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAdministrationOutlineService.class).getUserTableData(filter));
  }

  @Order(10.0)
  @ClassId("b5318627-9985-4786-b102-c8780d8484da")
  public class Table extends AbstractTable {

    public UsernameColumn getUsernameColumn() {
      return getColumnSet().getColumnByClass(UsernameColumn.class);
    }

    public UserNrColumn getUserNrColumn() {
      return getColumnSet().getColumnByClass(UserNrColumn.class);
    }

    @Order(1000)
    @ClassId("f08a953e-bd83-47fd-bc95-37aaf1a8155a")
    public class UserNrColumn extends AbstractLongColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    @ClassId("f7f0a1df-902f-48f0-bddc-3648f1a4d9ff")
    public class UsernameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(1000)
    @ClassId("2f91abaf-a0d6-4bc7-9101-adbd02b58a3f")
    public class NewUserMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewUser");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected void execAction() {
        UserForm form = new UserForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(2000)
    @ClassId("18e65bec-36ac-4c6d-8adb-63d26f3b2011")
    public class EditUserMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditUser");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection);
      }

      @Override
      protected void execAction() {
        UserForm form = new UserForm();
        form.setUserNr(getUserNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

  }

}

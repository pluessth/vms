package ch.pproject.vms.client.accounting.account;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.client.accounting.ui.desktop.outlines.pages.AccountingYearNodePage;
import ch.pproject.vms.shared.accounting.account.AccountingYearTablePageData;
import ch.pproject.vms.shared.accounting.services.common.IAccountingOutlineService;
import org.eclipse.scout.rt.platform.classid.ClassId;

@PageData(AccountingYearTablePageData.class)
@ClassId("acc06828-6a94-4b8d-a109-0ca402a9e2fc")
public class AccountingYearTablePage extends AbstractPageWithTable<AccountingYearTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AccountingYear");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    AccountingYearNodePage nodePage = new AccountingYearNodePage();
    nodePage.setAccountingYearNr(getTable().getAccountingYearNrColumn().getValue(row));
    return nodePage;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAccountingOutlineService.class).getAllAccountingYearTableData());
  }

  @Order(10.0)
  @ClassId("d5494c58-048b-41a7-bb32-946247be6b98")
  public class Table extends AbstractTable {

    /**
     * @return the FromColumn
     */
    public FromColumn getFromColumn() {
      return getColumnSet().getColumnByClass(FromColumn.class);
    }

    /**
     * @return the ToColumn
     */
    public ToColumn getToColumn() {
      return getColumnSet().getColumnByClass(ToColumn.class);
    }

    public AccountingYearNrColumn getAccountingYearNrColumn() {
      return getColumnSet().getColumnByClass(AccountingYearNrColumn.class);
    }

    public ClosedColumn getClosedColumn() {
      return getColumnSet().getColumnByClass(ClosedColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(10.0)
    @ClassId("d36669d2-26b1-427a-a5de-0517dd7ceca5")
    public class AccountingYearNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(20.0)
    @ClassId("cac909c2-81ea-4dc8-892f-81065023484d")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(30.0)
    @ClassId("0963c938-9a23-45d6-a953-3552b9f52157")
    public class FromColumn extends AbstractDateColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("From");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(40.0)
    @ClassId("9d81fe86-dcec-4245-aa7e-84bc5a0530b2")
    public class ToColumn extends AbstractDateColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("To");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(50.0)
    @ClassId("f6d49d43-f407-499c-9756-381714ca3ab9")
    public class ClosedColumn extends AbstractBooleanColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Closed");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(10.0)
    @ClassId("c56e9510-7767-45af-8f1f-91d987a0b740")
    public class NewAccountingYearMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.Null, TableMenuType.EmptySpace, TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewAccountingYear");
      }

      @Override
      protected void execAction() {
        AccountingYearForm form = new AccountingYearForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    @ClassId("48f18601-312d-430c-993d-6130d821d501")
    public class ModifyChartOfAccountsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyAccountingYear");
      }

      @Override
      protected void execAction() {
        AccountingYearForm form = new AccountingYearForm();
        form.setAccountingYearNr(getAccountingYearNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(40.0)
    @ClassId("57b63c14-a79f-42b1-878c-10bc1116373e")
    public class SeparatorMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredSeparator() {
        return true;
      }
    }

    @Order(70.0)
    @ClassId("2edf6713-fcea-4323-a03d-baeeed6f5c22")
    public class CopyChartOfAccountsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("CopyChartOfAccounts");
      }

      @Override
      protected void execAction() {
        AccountingYearForm form = new AccountingYearForm();
        form.setAccountingYearNr(getAccountingYearNrColumn().getSelectedValue());
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}

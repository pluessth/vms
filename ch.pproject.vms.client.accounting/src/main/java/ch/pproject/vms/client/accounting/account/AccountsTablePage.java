package ch.pproject.vms.client.accounting.account;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.client.accounting.posting.PostingsPerAccountTablePage;
import ch.pproject.vms.shared.accounting.account.AccountTypeCodeType;
import ch.pproject.vms.shared.accounting.account.AccountsTablePageData;
import ch.pproject.vms.shared.accounting.services.common.IAccountingOutlineService;
import org.eclipse.scout.rt.platform.classid.ClassId;

@PageData(AccountsTablePageData.class)
@ClassId("e18b434d-3de5-4aff-b6e4-5a851e039a95")
public class AccountsTablePage extends AbstractPageWithTable<AccountsTablePage.Table> {

  private Long m_accountingYearNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Accounts");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    PostingsPerAccountTablePage childPage = new PostingsPerAccountTablePage();
    childPage.setAccountNr(getTable().getAccountNrColumn().getValue(row));
    return childPage;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAccountingOutlineService.class).getAccountsTableData(getAccountingYearNr()));
  }

  @FormData
  public Long getAccountingYearNr() {
    return m_accountingYearNr;
  }

  @FormData
  public void setAccountingYearNr(Long accountingYearNr) {
    m_accountingYearNr = accountingYearNr;
  }

  @Order(10.0)
  @ClassId("355daa55-0b5e-491b-811b-bf2bc40a88f3")
  public class Table extends AbstractTable {

    public AccountNrColumn getAccountNrColumn() {
      return getColumnSet().getColumnByClass(AccountNrColumn.class);
    }

    public DescriptionColumn getDescriptionColumn() {
      return getColumnSet().getColumnByClass(DescriptionColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public TypeColumn getTypeColumn() {
      return getColumnSet().getColumnByClass(TypeColumn.class);
    }

    @Order(10.0)
    @ClassId("f5c2c6cd-35e7-47a6-aacc-4785eb857f3e")
    public class AccountNrColumn extends AbstractLongColumn {

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
    @ClassId("f08dd53e-91ca-4840-b0e5-3b6f7696480d")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(30.0)
    @ClassId("734a7f3c-6f5e-4473-8d0b-db31c0abf748")
    public class TypeColumn extends AbstractSmartColumn<Long> {

      @Override
      protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
        return AccountTypeCodeType.class;

      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Type");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(40.0)
    @ClassId("b7c3e946-d598-4476-87ea-94c67938bc4e")
    public class DescriptionColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Description");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(10.0)
    @ClassId("b45660d5-50c8-4415-bbd3-c1dd6cb39d8d")
    public class NewAccountMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.Null, CalendarMenuType.EmptySpace, TableMenuType.EmptySpace, TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewAccount");
      }

      @Override
      protected void execAction() {
        AccountForm form = new AccountForm();
        form.getAccountingYearField().setValue(getAccountingYearNr());
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    @ClassId("2741b5ea-5cf6-49f0-bff7-d3b8fe94ca02")
    public class ModifyAccountMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyAccount");
      }

      @Override
      protected void execAction() {
        AccountForm form = new AccountForm();
        form.setAccountNr(getAccountNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}

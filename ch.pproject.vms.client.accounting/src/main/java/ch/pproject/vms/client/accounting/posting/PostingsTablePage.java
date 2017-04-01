package ch.pproject.vms.client.accounting.posting;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import ch.pproject.vms.shared.accounting.account.AccountingYearLookupCall;
import ch.pproject.vms.shared.accounting.account.IAccountingYearService;
import ch.pproject.vms.shared.accounting.posting.PostingSearchFormData;
import ch.pproject.vms.shared.accounting.posting.PostingsTablePageData;
import ch.pproject.vms.shared.accounting.services.code.PostingStatusCodeType;
import ch.pproject.vms.shared.accounting.services.common.IAccountingOutlineService;
import ch.pproject.vms.shared.core.person.PersonLookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@PageData(PostingsTablePageData.class)
@ClassId("1584c1d3-d8d2-42e1-8b84-5a7fb7e01ad3")
public class PostingsTablePage extends AbstractPageWithTable<PostingsTablePage.Table> {

  private Long m_accountingYearNr;

  public Long getAccountingYearNr() {
    return m_accountingYearNr;
  }

  public void setAccountingYearNr(Long accountingYearNr) {
    m_accountingYearNr = accountingYearNr;
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return PostingSearchForm.class;
  }

  @Override
  protected void execInitSearchForm() {
    PostingSearchForm searchForm = (PostingSearchForm) getSearchFormInternal();
    if (getAccountingYearNr() != null) {
      searchForm.getAccountingYearField().setValue(getAccountingYearNr());
      searchForm.getAccountingYearField().setEnabled(false);
    }
    else {
      searchForm.getAccountingYearField().setValue(BEANS.get(IAccountingYearService.class).getCurrentOrLatestAccountingYearNr());
    }
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Postings");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAccountingOutlineService.class).getPostingsTableData((PostingSearchFormData) filter.getFormData()));
  }

  @Order(10.0)
  @ClassId("ec5c5c0e-460d-498c-8276-af2115ac274b")
  public class Table extends AbstractTable {

    public ActivityColumn getActivityColumn() {
      return getColumnSet().getColumnByClass(ActivityColumn.class);
    }

    public AmountColumn getAmountColumn() {
      return getColumnSet().getColumnByClass(AmountColumn.class);
    }

    public AccountingYearColumn getAccountingYearColumn() {
      return getColumnSet().getColumnByClass(AccountingYearColumn.class);
    }

    public CreditAccountColumn getCreditAccountColumn() {
      return getColumnSet().getColumnByClass(CreditAccountColumn.class);
    }

    public DebitAccountColumn getDebitAccountColumn() {
      return getColumnSet().getColumnByClass(DebitAccountColumn.class);
    }

    public PersonColumn getPersonColumn() {
      return getColumnSet().getColumnByClass(PersonColumn.class);
    }

    public PostingDateColumn getPostingDateColumn() {
      return getColumnSet().getColumnByClass(PostingDateColumn.class);
    }

    public PostingNrColumn getPostingNrColumn() {
      return getColumnSet().getColumnByClass(PostingNrColumn.class);
    }

    public PostingTextColumn getPostingTextColumn() {
      return getColumnSet().getColumnByClass(PostingTextColumn.class);
    }

    public StatusColumn getStatusColumn() {
      return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    public VoucherNoColumn getVoucherNoColumn() {
      return getColumnSet().getColumnByClass(VoucherNoColumn.class);
    }

    @Order(10.0)
    @ClassId("d3a462ce-f76e-4788-9a95-8f02c43e5f25")
    public class PostingNrColumn extends AbstractLongColumn {

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
    @ClassId("26a60942-b3a5-45ea-98aa-38502e9137de")
    public class VoucherNoColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Voucher");
      }

      @Override
      protected int getConfiguredWidth() {
        return 70;
      }
    }

    @Order(30.0)
    @ClassId("91eb627f-7862-4f15-b6d2-2a3ed02e3be6")
    public class PostingDateColumn extends AbstractDateColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PostingDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(40.0)
    @ClassId("7ae08f5b-5a71-47f9-98c5-61c580297464")
    public class AccountingYearColumn extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ChartOfAccounts");
      }

      @Override
      protected Class<? extends LookupCall<Long>> getConfiguredLookupCall() {
        return AccountingYearLookupCall.class;

      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(50.0)
    @ClassId("5b4f5525-26e4-4eff-846b-cff05d61b141")
    public class DebitAccountColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DebitAccount");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(60.0)
    @ClassId("331f3355-c056-46c5-87e3-9c4e76a6efd8")
    public class CreditAccountColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("CreditAccount");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(70.0)
    @ClassId("5f0ec317-6b10-45df-8b43-c161048258b9")
    public class AmountColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Amount");
      }
    }

    @Order(80.0)
    @ClassId("11ab6559-0fb7-41e9-b508-bdc146e30641")
    public class PostingTextColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PostingText");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(90.0)
    @ClassId("8dae3919-2976-4f58-997e-5abdc4cd9e8f")
    public class StatusColumn extends AbstractSmartColumn<Long> {

      @Override
      protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
        return PostingStatusCodeType.class;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Status");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        if (PostingStatusCodeType.EnteredStatusCode.ID.equals(cell.getValue())) {
          cell.setBackgroundColor("a0f0a0");
        }
        else if (PostingStatusCodeType.OpenStatusCode.ID.equals(cell.getValue())) {
          cell.setBackgroundColor("f0f0a0");
        }
      }
    }

    @Order(100.0)
    @ClassId("c1d5340a-b1ac-4b02-a881-33c5f99488eb")
    public class PersonColumn extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Person");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }

      @Override
      protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
        return PersonLookupCall.class;
      }
    }

    @Order(110.0)
    @ClassId("d293d41d-5092-46ae-b5ef-3f1fe8cf7d2d")
    public class ActivityColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Activity");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(10.0)
    @ClassId("163c5c9f-f163-481e-ae79-81fe1403568d")
    public class NewPostingMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.Null, CalendarMenuType.EmptySpace, TableMenuType.EmptySpace, TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewPosting");
      }

      @Override
      protected void execAction() {
        PostingForm form = new PostingForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    @ClassId("866d19c3-edc7-42d6-aa39-86a4021ac17f")
    public class ModifyPostingMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyPosting");
      }

      @Override
      protected void execAction() {
        PostingForm form = new PostingForm();
        form.setPostingNr(getPostingNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}

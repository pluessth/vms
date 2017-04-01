package ch.pproject.vms.client.accounting.posting;

import java.math.BigDecimal;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.ColorUtility;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.client.accounting.posting.PostingsPerAccountTablePage.Table.AmountCreditColumn;
import ch.pproject.vms.client.accounting.posting.PostingsPerAccountTablePage.Table.AmountDebitColumn;
import ch.pproject.vms.client.accounting.posting.PostingsPerAccountTablePage.Table.ColumnSortingColumn;
import ch.pproject.vms.client.accounting.posting.PostingsPerAccountTablePage.Table.VoucherNoColumn;
import ch.pproject.vms.shared.accounting.account.AccountFormData;
import ch.pproject.vms.shared.accounting.account.AccountTypeCodeType;
import ch.pproject.vms.shared.accounting.account.IAccountService;
import ch.pproject.vms.shared.accounting.posting.PostingsPerAccountTablePageData;
import ch.pproject.vms.shared.accounting.services.common.IAccountingOutlineService;

@PageData(PostingsPerAccountTablePageData.class)
@ClassId("4b900592-ff45-43d5-a9ab-d472069175a6")
public class PostingsPerAccountTablePage extends AbstractPageWithTable<PostingsPerAccountTablePage.Table> {

  private Long m_accountNr;
  private Long m_accountType;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Postings");
  }

  @Override
  protected void execInitPage() {
    super.execInitPage();

    AccountFormData accountFormData = new AccountFormData();
    accountFormData.setAccountNr(getAccountNr());
    accountFormData = BEANS.get(IAccountService.class).load(accountFormData);
    setAccountType(accountFormData.getType().getValue());
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IAccountingOutlineService.class).getPostingsPerAccountTableData(getAccountNr()));
  }

  @Override
  protected void execPopulateTable() {
    super.execPopulateTable();

    ColumnSortingColumn sortingCol = getTable().getColumnSortingColumn();
    VoucherNoColumn postingNumberCol = getTable().getVoucherNoColumn();
    AmountCreditColumn amountCreditCol = getTable().getAmountCreditColumn();
    AmountDebitColumn amountDebitCol = getTable().getAmountDebitColumn();

    // calcualte balance
    BigDecimal amountDebit = BigDecimal.ZERO;
    BigDecimal amountCredit = BigDecimal.ZERO;
    for (ITableRow row : getTable().getRows()) {
      BigDecimal amountDebitValue = amountDebitCol.getValue(row);
      if (amountDebitValue != null) {
        amountDebit = amountDebit.add(amountDebitValue);
      }
      BigDecimal amountCreditValue = amountCreditCol.getValue(row);
      if (amountCreditValue != null) {
        amountCredit = amountCredit.add(amountCreditValue);
      }
    }
    BigDecimal balanceDebit = amountCredit.subtract(amountDebit);
    BigDecimal balanceCredit = amountDebit.subtract(amountCredit);

    TableRow balanceRow = new TableRow(getTable().getColumnSet());
    balanceRow.setCellValue(sortingCol.getColumnIndex(), 1);
    balanceRow.setCellValue(postingNumberCol.getColumnIndex(), TEXTS.get("Balance"));

    // add control total row
    TableRow controlTotalRow = new TableRow(getTable().getColumnSet());
    controlTotalRow.setCellValue(postingNumberCol.getColumnIndex(), TEXTS.get("ControlTotal"));
    controlTotalRow.setCellValue(sortingCol.getColumnIndex(), 2);

    if (CompareUtility.isOneOf(getAccountType(), AccountTypeCodeType.NominalAccountCode.ExpenseAccountCode.ID, AccountTypeCodeType.InventoryAccountCode.AssetAccountCode.ID)) {
      balanceRow.setCellValue(amountCreditCol.getColumnIndex(), balanceCredit);
      amountDebitCol.setValue(controlTotalRow, amountDebit);
      amountCreditCol.setValue(controlTotalRow, amountCredit.add(balanceCredit));
    }

    if (CompareUtility.isOneOf(getAccountType(), AccountTypeCodeType.NominalAccountCode.IncomeAccountCode.ID, AccountTypeCodeType.InventoryAccountCode.LiabilityAccountCode.ID)) {
      balanceRow.setCellValue(amountDebitCol.getColumnIndex(), balanceDebit);
      amountDebitCol.setValue(controlTotalRow, amountDebit.add(balanceDebit));
      amountCreditCol.setValue(controlTotalRow, amountCredit);
    }
    getTable().addRow(balanceRow);

    getTable().addRow(controlTotalRow);
  }

  @FormData
  public Long getAccountNr() {
    return m_accountNr;
  }

  @FormData
  public void setAccountNr(Long accountNr) {
    m_accountNr = accountNr;
  }

  @FormData
  public Long getAccountType() {
    return m_accountType;
  }

  @FormData
  public void setAccountType(Long accountType) {
    m_accountType = accountType;
  }

  @Order(10.0)
  @ClassId("b38deb03-4e45-424e-83fd-00a6e87d06df")
  public class Table extends AbstractTable {

    @Override
    protected void execDecorateRow(ITableRow row) {
      super.execDecorateRow(row);
      ColumnSortingColumn sortingCol = getTable().getColumnSortingColumn();
      if ((int) row.getCellValue(sortingCol.getColumnIndex()) == 1) {
        row.setForegroundColor(ColorUtility.RED);
      }
      if ((int) row.getCellValue(sortingCol.getColumnIndex()) == 2) {
        row.setFont(FontSpec.parse("bold"));
      }
    }

    public AmountCreditColumn getAmountCreditColumn() {
      return getColumnSet().getColumnByClass(AmountCreditColumn.class);
    }

    public AmountDebitColumn getAmountDebitColumn() {
      return getColumnSet().getColumnByClass(AmountDebitColumn.class);
    }

    public ColumnSortingColumn getColumnSortingColumn() {
      return getColumnSet().getColumnByClass(ColumnSortingColumn.class);
    }

    public PostingDateColumn getPostingDateColumn() {
      return getColumnSet().getColumnByClass(PostingDateColumn.class);
    }

    public PostingNrColumn getPostingNrColumn() {
      return getColumnSet().getColumnByClass(PostingNrColumn.class);
    }

    public PostingTextCreditColumn getPostingTextCreditColumn() {
      return getColumnSet().getColumnByClass(PostingTextCreditColumn.class);
    }

    public PostingTextDebitColumn getPostingTextDebitColumn() {
      return getColumnSet().getColumnByClass(PostingTextDebitColumn.class);
    }

    public VoucherNoColumn getVoucherNoColumn() {
      return getColumnSet().getColumnByClass(VoucherNoColumn.class);
    }

    @Order(10.0)
    @ClassId("9ebd1432-cc9d-4e17-b781-01d0cfdd4b77")
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
    @ClassId("eca1bd2d-0a07-4e91-bf43-e24b3f7d5e17")
    public class VoucherNoColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("VoucherNo");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(30.0)
    @ClassId("eed41998-605a-44de-a1f7-5c24b320327a")
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
    @ClassId("af1d6c83-bf0a-4117-a85e-cbb798d7785c")
    public class PostingTextDebitColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PostingText");
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(50.0)
    @ClassId("3c5e04d9-d0d4-469d-94c1-763612f4ba2a")
    public class AmountDebitColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Debit");
      }

      @Override
      protected int getConfiguredWidth() {
        return 90;
      }
    }

    @Order(60.0)
    @ClassId("0edc50f2-7edb-4310-914a-524fc6af7b67")
    public class AmountCreditColumn extends AbstractBigDecimalColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Credit");
      }

      @Override
      protected int getConfiguredWidth() {
        return 90;
      }
    }

    @Order(70.0)
    @ClassId("55838ad7-ad8e-4cb4-968e-998eed604987")
    public class PostingTextCreditColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PostingText");
      }

      @Override
      protected int getConfiguredWidth() {
        return 250;
      }
    }

    @Order(80.0)
    @ClassId("36df00e4-4052-4b76-b32e-3c52460290b5")
    public class ColumnSortingColumn extends AbstractIntegerColumn {

      @Override
      protected boolean getConfiguredAlwaysIncludeSortAtBegin() {
        return true;
      }

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ColumnSorting");
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 0;
      }
    }

    @Order(10.0)
    @ClassId("68de2057-c125-46c5-ab7c-4aad83e7c213")
    public class ModifyPostingMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyPosting");
      }

      @Override
      protected void execOwnerValueChanged(Object newOwnerValue) {
        setVisible(getPostingNrColumn().getSelectedValue() != null);
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

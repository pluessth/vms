package ch.pproject.vms.shared.accounting.account;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.vms.client.accounting.account.AccountsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class AccountsTablePageData extends AbstractTablePageData {

  private static final long serialVersionUID = 1L;

  @Override
  public AccountsTableRowData addRow() {
    return (AccountsTableRowData) super.addRow();
  }

  @Override
  public AccountsTableRowData addRow(int rowState) {
    return (AccountsTableRowData) super.addRow(rowState);
  }

  @Override
  public AccountsTableRowData createRow() {
    return new AccountsTableRowData();
  }

  @Override
  public Class<? extends AbstractTableRowData> getRowType() {
    return AccountsTableRowData.class;
  }

  @Override
  public AccountsTableRowData[] getRows() {
    return (AccountsTableRowData[]) super.getRows();
  }

  @Override
  public AccountsTableRowData rowAt(int index) {
    return (AccountsTableRowData) super.rowAt(index);
  }

  public void setRows(AccountsTableRowData[] rows) {
    super.setRows(rows);
  }

  public static class AccountsTableRowData extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;
    public static final String accountNr = "accountNr";
    public static final String name = "name";
    public static final String type = "type";
    public static final String description = "description";
    private Long m_accountNr;
    private String m_name;
    private Long m_type;
    private String m_description;

    public Long getAccountNr() {
      return m_accountNr;
    }

    public void setAccountNr(Long newAccountNr) {
      m_accountNr = newAccountNr;
    }

    public String getName() {
      return m_name;
    }

    public void setName(String newName) {
      m_name = newName;
    }

    public Long getType() {
      return m_type;
    }

    public void setType(Long newType) {
      m_type = newType;
    }

    public String getDescription() {
      return m_description;
    }

    public void setDescription(String newDescription) {
      m_description = newDescription;
    }
  }
}

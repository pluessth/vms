package ch.pproject.vms.shared.core.person;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@ClassId("889940ac-3bbd-4a89-a30d-2df2cdf377f2-formdata")
@Generated(value = "ch.pproject.vms.client.core.person.PassiveMemberTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class PassiveMemberTablePageData extends AbstractPersonTablePageData {

  private static final long serialVersionUID = 1L;

  @Override
  public PassiveMemberTableRowData addRow() {
    return (PassiveMemberTableRowData) super.addRow();
  }

  @Override
  public PassiveMemberTableRowData addRow(int rowState) {
    return (PassiveMemberTableRowData) super.addRow(rowState);
  }

  @Override
  public PassiveMemberTableRowData createRow() {
    return new PassiveMemberTableRowData();
  }

  @Override
  public Class<? extends AbstractTableRowData> getRowType() {
    return PassiveMemberTableRowData.class;
  }

  @Override
  public PassiveMemberTableRowData[] getRows() {
    return (PassiveMemberTableRowData[]) super.getRows();
  }

  @Override
  public PassiveMemberTableRowData rowAt(int index) {
    return (PassiveMemberTableRowData) super.rowAt(index);
  }

  public void setRows(PassiveMemberTableRowData[] rows) {
    super.setRows(rows);
  }

  public static class PassiveMemberTableRowData extends AbstractPersonTableRowData {

    private static final long serialVersionUID = 1L;
  }
}

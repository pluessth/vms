package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("d64f3d8e-2667-4ddf-a3b3-1b6f3bcdadae")
public abstract class AbstractValidatableSmartLongColumn extends AbstractSmartColumn<Long> implements IValidatableColumn {

  @Override
  protected int getConfiguredWidth() {
    return 120;
  }

  @Override
  protected boolean getConfiguredEditable() {
    return true;
  }

}

package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("c7362132-c223-4261-9a3e-9bee002c901b")
public abstract class AbstractValidatableStringColumn extends AbstractStringColumn implements IValidatableColumn {

  protected abstract Class<?> getValidationDataType();

  @Override
  protected int getConfiguredWidth() {
    return 120;
  }

  @Override
  protected boolean getConfiguredEditable() {
    return true;
  }

}

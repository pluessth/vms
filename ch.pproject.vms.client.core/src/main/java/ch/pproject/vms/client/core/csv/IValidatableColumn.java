package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

public interface IValidatableColumn {

  Class<? extends AbstractValueFieldData> getFormFieldType();

}

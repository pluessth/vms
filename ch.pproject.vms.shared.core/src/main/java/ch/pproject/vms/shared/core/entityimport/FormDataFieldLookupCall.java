package ch.pproject.vms.shared.core.entityimport;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("d066e459-d67f-4683-8f71-1fd268f48f81")
public class FormDataFieldLookupCall extends LocalLookupCall<Class<? extends AbstractValueFieldData<?>>> {

  private static final long serialVersionUID = 1L;

  @Override
  protected boolean getConfiguredMasterRequired() {
    return true;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected List<LookupRow<Class<? extends AbstractValueFieldData<?>>>> execCreateLookupRows()  {
    List<LookupRow<Class<? extends AbstractValueFieldData<?>>>> rows = new ArrayList<LookupRow<Class<? extends AbstractValueFieldData<?>>>>();
    Class<? extends AbstractFormData> formDataType = (Class<? extends AbstractFormData>) getMaster();
    if (formDataType != null) {
      for (Class<?> fieldClass : formDataType.getDeclaredClasses()) {
        // check if it is field data
        if (AbstractValueFieldData.class.isAssignableFrom(fieldClass)) {
          String text = TEXTS.getWithFallback(fieldClass.getSimpleName(), fieldClass.getSimpleName());
          rows.add(new LookupRow<Class<? extends AbstractValueFieldData<?>>>((Class<? extends AbstractValueFieldData<?>>) fieldClass, text));
        }
      }
    }
    return rows;
  }
}

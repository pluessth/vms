package ch.pproject.vms.client.core.entity;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public abstract class AbstractEntity implements IEntity {

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends AbstractFormData> getFormDataClass() {
    FormData fd = getFormClass().getAnnotation(FormData.class);
    return fd.value();
  }

}

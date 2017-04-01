package ch.pproject.vms.client.core.entity;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

@ApplicationScoped
public interface IEntity {

  String getText();

  Class<? extends AbstractForm> getFormClass();

  Class<? extends AbstractFormData> getFormDataClass();

}

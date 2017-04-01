package ch.pproject.vms.client.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import ch.pproject.vms.client.core.entity.IEntity;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("81ca1cfc-51c6-4824-a9ba-3bb2334e072e")
public class ImportableEntityFormLookupCall extends LocalLookupCall<Class<? extends AbstractForm>> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<Class<? extends AbstractForm>>> execCreateLookupRows() {
    List<LookupRow<Class<? extends AbstractForm>>> rows = new ArrayList<LookupRow<Class<? extends AbstractForm>>>();
    for (IEntity entity : BEANS.all(IEntity.class)) {
      rows.add(new LookupRow<Class<? extends AbstractForm>>(entity.getFormClass(), entity.getText()));
    }
    return rows;
  }
}

package ch.pproject.vms.shared.core.administration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import ch.pproject.vms.shared.core.security.IVmsPermission;

public class PermissionLookupCall extends LocalLookupCall<Class> {
  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<Class>> execCreateLookupRows() {
    List<LookupRow<Class>> rows = new ArrayList<LookupRow<Class>>();
    List<IVmsPermission> permissions = BEANS.all(IVmsPermission.class);
    for (IVmsPermission permission : permissions) {
      rows.add(new LookupRow<Class>(permission.getClass(), permission.getClass().getSimpleName()));
    }
    return rows;
  }

}

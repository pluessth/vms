package ch.pproject.vms.client.core.ui.desktop.outlines;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.shared.core.Icons;
import ch.pproject.vms.shared.core.activity.ReadActivityOutlinePermission;

@ClassId("d983b5ad-fe4a-40f8-a9dc-8838dcbac1fe")
public class ActivityOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Activities");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Calendar;
  }

  @Override
  protected boolean getConfiguredVisible() {
    return ACCESS.check(new ReadActivityOutlinePermission());
  }
}

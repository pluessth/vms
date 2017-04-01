package ch.pproject.vms.client.core.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.client.core.administration.UserTablePage;
import ch.pproject.vms.shared.core.Icons;
import ch.pproject.vms.shared.core.administration.ReadAdministrationOutlinePermission;

@ClassId("a2fa6c67-fe74-4cdc-bab2-54568a0f5be9")
public class AdministrationOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Gear;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    pageList.add(new UserTablePage());
  }

  @Override
  protected boolean getConfiguredVisible() {
    return ACCESS.check(new ReadAdministrationOutlinePermission());
  }
}

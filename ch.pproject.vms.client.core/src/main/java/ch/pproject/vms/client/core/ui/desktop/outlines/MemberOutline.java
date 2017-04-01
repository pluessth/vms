package ch.pproject.vms.client.core.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.client.core.person.ActiveMemberTablePage;
import ch.pproject.vms.client.core.person.AllPersonTablePage;
import ch.pproject.vms.client.core.person.PassiveMemberTablePage;
import ch.pproject.vms.client.core.team.TeamTablePage;
import ch.pproject.vms.shared.core.Icons;
import ch.pproject.vms.shared.core.person.ReadMemberOutlinePermission;

@ClassId("f8f8e2ff-aff3-4ad0-a1ea-1ec2f156865b")
public class MemberOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Members");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Users;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    pageList.add(new ActiveMemberTablePage());
    pageList.add(new PassiveMemberTablePage());
    pageList.add(new AllPersonTablePage());
    pageList.add(new TeamTablePage());
  }

  @Override
  protected boolean getConfiguredVisible() {
    return ACCESS.check(new ReadMemberOutlinePermission());
  }
}

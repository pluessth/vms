package ch.pproject.vms.client.core.person;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.person.ActiveMemberTablePageData;
import ch.pproject.vms.shared.core.person.PersonSearchFormData;
import ch.pproject.vms.shared.core.services.IMemberOutlineService;
import ch.pproject.vms.shared.core.services.code.RoleCodeType;

@PageData(ActiveMemberTablePageData.class)
@ClassId("3e24a0ec-07d0-4b0e-affa-2bf1a0f1547d")
public class ActiveMemberTablePage extends AbstractPersonTablePage {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ActiveMembers");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    ((PersonSearchFormData) filter.getFormData()).setRoleUid(RoleCodeType.ActiveMemberCode.ID);
    importPageData(BEANS.get(IMemberOutlineService.class).getMembersTableData(filter));
  }

}

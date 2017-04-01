package ch.pproject.vms.client.core.person;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.person.PassiveMemberTablePageData;
import ch.pproject.vms.shared.core.person.PersonSearchFormData;
import ch.pproject.vms.shared.core.services.IMemberOutlineService;
import ch.pproject.vms.shared.core.services.code.RoleCodeType;

@PageData(PassiveMemberTablePageData.class)
@ClassId("889940ac-3bbd-4a89-a30d-2df2cdf377f2")
public class PassiveMemberTablePage extends AbstractPersonTablePage {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PassiveMembers");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    ((PersonSearchFormData) filter.getFormData()).setRoleUid(RoleCodeType.PassiveMemberCode.ID);
    importPageData(BEANS.get(IMemberOutlineService.class).getMembersTableData(filter));
  }

}

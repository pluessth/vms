package ch.pproject.vms.client.core.person;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.shared.core.person.AllPersonTablePageData;
import ch.pproject.vms.shared.core.services.IMemberOutlineService;
import org.eclipse.scout.rt.platform.classid.ClassId;

@PageData(AllPersonTablePageData.class)
@ClassId("c4629f2e-26db-4349-b347-40f4f8e7214c")
public class AllPersonTablePage extends AbstractPersonTablePage {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AllPersons");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IMemberOutlineService.class).getAllPersonsTableData(filter));
  }
}

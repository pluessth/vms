package ch.pproject.vms.shared.core.services;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IAdministrationOutlineService extends IService {

  AbstractTablePageData getUserTableData(SearchFilter filter);

  AbstractTablePageData getUserPermissionTableData(Long userNr, SearchFilter filter);

}

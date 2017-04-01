package ch.pproject.vms.shared.core.services;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IMemberOutlineService extends IService {

  AbstractTablePageData getAllPersonsTableData(SearchFilter filter);

  AbstractTablePageData getMembersTableData(SearchFilter filter);

  AbstractTablePageData getTeamTableData(SearchFilter filter);
}

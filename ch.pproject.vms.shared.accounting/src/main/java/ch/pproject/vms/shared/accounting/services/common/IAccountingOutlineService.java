package ch.pproject.vms.shared.accounting.services.common;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import ch.pproject.vms.shared.accounting.posting.PostingSearchFormData;

@TunnelToServer
public interface IAccountingOutlineService extends IService {

  AbstractTablePageData getPostingsTableData(PostingSearchFormData formData);

  AbstractTablePageData getAccountsTableData(Long chartOfAccountsNr);

  AbstractTablePageData getAllAccountingYearTableData();

  AbstractTablePageData getPostingsPerAccountTableData(Long accountNr);
}

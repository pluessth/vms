package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAccountingYearService extends IService {

  AccountingYearFormData prepareCreate(AccountingYearFormData formData);

  AccountingYearFormData create(AccountingYearFormData formData);

  AccountingYearFormData load(AccountingYearFormData formData);

  AccountingYearFormData store(AccountingYearFormData formData);

  public Long getCurrentOrLatestAccountingYearNr();

  boolean isClosed(Long accountingYearNr);
}

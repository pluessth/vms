package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAccountService extends IService {

  AccountFormData prepareCreate(AccountFormData formData);

  AccountFormData create(AccountFormData formData);

  AccountFormData load(AccountFormData formData);

  AccountFormData store(AccountFormData formData);

  public void copyAccountsToAccountingYear(Long oldAccountingYearNr, Long newAccountingYearNr);
}

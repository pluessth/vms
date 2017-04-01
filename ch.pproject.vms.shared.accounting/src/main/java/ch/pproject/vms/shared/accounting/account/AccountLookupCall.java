package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("f69c5f46-315f-448b-ad66-56f4dc26b74c")
public class AccountLookupCall extends LookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IAccountLookupService.class;
  }
}

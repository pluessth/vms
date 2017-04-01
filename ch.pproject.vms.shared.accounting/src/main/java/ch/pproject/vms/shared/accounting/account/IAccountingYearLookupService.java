package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IAccountingYearLookupService extends ILookupService<Long> {
}

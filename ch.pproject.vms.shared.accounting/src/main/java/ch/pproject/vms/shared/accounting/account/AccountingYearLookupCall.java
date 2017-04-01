package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("8e3a7b48-6621-471d-92c9-e46fdd94a0cb")
public class AccountingYearLookupCall extends LookupCall<Long> {

  private static final long serialVersionUID = 1L;
  private boolean m_showClosedAccountingYears;

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IAccountingYearLookupService.class;
  }

  public boolean getShowClosedAccountingYears() {
    return m_showClosedAccountingYears;
  }

  public void setShowClosedAccountingYears(boolean showClosedAccountingYears) {
    m_showClosedAccountingYears = showClosedAccountingYears;
  }
}

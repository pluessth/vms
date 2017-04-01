package ch.pproject.vms.shared.core.person;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("1d6c1208-68ee-4d17-a7c5-aa297daed77c")
public class PersonLookupCall extends LookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IPersonLookupService.class;
  }
}

package ch.pproject.vms.shared.core.services.common.text;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;

@Order(6000)
public class DefaultTextProviderService extends AbstractDynamicNlsTextProviderService {

  @Override
  protected String getDynamicNlsBaseName() {
    return "ch.pproject.vms.shared.core.texts.Texts";
  }
}

package ch.pproject.vms.shared.accounting.services.common.text;

import org.eclipse.scout.rt.platform.Order;

import ch.pproject.vms.shared.core.services.common.text.DefaultTextProviderService;

@Order(7000)
public class AccountingTextProviderService extends DefaultTextProviderService {

  @Override
  protected String getDynamicNlsBaseName() {
    return "ch.pproject.vms.shared.accounting.texts.Texts";
  }
}

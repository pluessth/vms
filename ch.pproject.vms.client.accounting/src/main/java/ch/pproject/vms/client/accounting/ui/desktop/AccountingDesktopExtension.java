package ch.pproject.vms.client.accounting.ui.desktop;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import ch.pproject.vms.client.accounting.ui.desktop.outlines.AccountingOutline;

public class AccountingDesktopExtension extends AbstractDesktopExtension {

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(AccountingOutline.class);
  }

  @Order(2500)
  @ClassId("14a6b743-9b4a-48eb-ade6-890f749ab79d")
  public class AccountingOutlineViewButton extends AbstractOutlineViewButton {

    public AccountingOutlineViewButton() {
      this(AccountingOutline.class);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    public AccountingOutlineViewButton(Class<? extends AccountingOutline> outlineClass) {
      super(getCoreDesktop(), outlineClass);
    }

  }

}

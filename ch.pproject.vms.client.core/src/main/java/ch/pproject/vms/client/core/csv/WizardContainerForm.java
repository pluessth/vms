package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.wizard.DefaultWizardContainerForm;
import org.eclipse.scout.rt.client.ui.wizard.IWizard;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;

import ch.pproject.vms.client.core.csv.WizardContainerForm.MainBox.Button;

@ClassId("704f82e8-ae3f-4deb-83a1-366b62203cf6")
public class WizardContainerForm extends DefaultWizardContainerForm {

  public WizardContainerForm(IWizard wizard) {
    super(wizard, true);
  }

  public WizardContainerForm(IWizard wizard, boolean callInitializer) {
    super(wizard, false);
    if (callInitializer) {
      callInitializer();
    }
  }

  public Button getButton() {
    return getFieldByClass(Button.class);
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @ClassId("19290116-006f-4045-a9ab-94a7b4c7fbd1")
  public class MainBox extends DefaultWizardContainerForm.MainBox {
    @Override
    protected int getConfiguredGridH() {
      return 20;
    }

    @Order(1000)
    @ClassId("4ceb5f43-eb70-46b3-af18-83108a54496a")
    public class Button extends AbstractCancelButton {
    }

  }
}

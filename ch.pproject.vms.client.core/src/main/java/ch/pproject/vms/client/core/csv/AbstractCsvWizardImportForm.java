package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("1f4f8ce5-118a-4090-9c0b-ebe0dc21f61d")
public abstract class AbstractCsvWizardImportForm extends AbstractForm {

  public AbstractCsvWizardImportForm(boolean callInitializer)  {
    super(callInitializer);
  }

  public AbstractCsvWizardImportForm()  {
    super(true);
  }

  @Override
  public CsvImportWizard getWizard() {
    return (CsvImportWizard) super.getWizard();
  }
}

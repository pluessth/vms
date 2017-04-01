package ch.pproject.vms.client.core.csv;

import java.util.Map;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.wizard.AbstractWizard;
import org.eclipse.scout.rt.client.ui.wizard.AbstractWizardStep;
import org.eclipse.scout.rt.client.ui.wizard.IWizardContainerForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("2cab2ef0-f22b-4761-b9d0-911ebceba986")
public class CsvImportWizard extends AbstractWizard {

  private AbstractFileHelper m_fileHelper;
  private Map<String, Class<? extends AbstractValueFieldData>> m_columnMapping;
  private Class<? extends AbstractForm> m_formType;

  public CsvImportWizard() {
    super();
  }

  @Override
  protected IWizardContainerForm execCreateContainerForm() {
    return new WizardContainerForm(this);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CSVImport");
  }

  public AbstractFileHelper getFileHelper() {
    return m_fileHelper;
  }

  public void setFileHelper(AbstractFileHelper fileHelper) {
    m_fileHelper = fileHelper;
  }

  public Map<String, Class<? extends AbstractValueFieldData>> getColumnMapping() {
    return m_columnMapping;
  }

  public void setColumnMapping(Map<String, Class<? extends AbstractValueFieldData>> columnMapping) {
    m_columnMapping = columnMapping;
  }

  public Class<? extends AbstractForm> getFormType() {
    return m_formType;
  }

  public void setFormType(Class<? extends AbstractForm> formDataType) {
    m_formType = formDataType;
  }

  public PreparationStep getPreparationStep() {
    return getStep(CsvImportWizard.PreparationStep.class);
  }

  public SelectionStep getSelectionStep() {
    return getStep(CsvImportWizard.SelectionStep.class);
  }

  @Order(10.0)
  @ClassId("2aed4977-7e0c-4841-bf25-2c9537dedf01")
  public class PreparationStep extends AbstractWizardStep<PreparationForm> {

    @Override
    protected void execActivate(int stepKind) {
      PreparationForm form = getForm();
      if (getForm() == null) {
        form = new PreparationForm();
        form.startWizardStep(this, PreparationForm.PreparationHandler.class);
        setForm(form);
      }
      getWizard().setWizardForm(form);
    }

    @Override
    protected void execDeactivate(int stepKind) {
      // Save the form if the user clicks next
      if (stepKind == STEP_NEXT) {
        PreparationForm form = getForm();
        if (form != null) {
          form.doSave();
        }
      }
    }

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Preparation");
    }
  }

  @Order(20.0)
  @ClassId("d1de7518-eaea-41e0-9fef-c69885013f7c")
  public class SelectionStep extends AbstractWizardStep<SelectionForm> {

    @Override
    protected void execActivate(int stepKind) {
      getContainerForm().getWizardFinishButton().setLabel("Import");

      SelectionForm form = getForm();
      if (getForm() == null) {
        form = new SelectionForm();
        form.startWizardStep(this, SelectionForm.SelectionHandler.class);
        setForm(form);
      }
      getWizard().setWizardForm(form);
    }

    @Override
    protected void execDeactivate(int stepKind) {
      // Save the form if the user clicks next
      if (stepKind == STEP_NEXT) {
        SelectionForm form = getForm();
        if (form != null) {
          form.doSave();
        }
      }
    }

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Selection");
    }
  }
}

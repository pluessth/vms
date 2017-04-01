package ch.pproject.vms.client.core.csv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.TypeCastUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.GroupBox;
import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.GroupBox.ColumnMappingField;
import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.GroupBox.EntityField;
import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.GroupBox.FileField;
import ch.pproject.vms.client.core.csv.PreparationForm.MainBox.OkButton;
import ch.pproject.vms.client.core.lookup.ImportableEntityFormLookupCall;
import ch.pproject.vms.shared.core.entityimport.FormDataFieldLookupCall;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("0464a71b-db3d-49e9-86c1-ff410866295b")
public class PreparationForm extends AbstractCsvWizardImportForm {

  public PreparationForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Preparation");
  }

  public void startPreparation() {
    startInternal(new PreparationForm.PreparationHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ColumnMappingField getColumnMappingField() {
    return getFieldByClass(ColumnMappingField.class);
  }

  public EntityField getEntityField() {
    return getFieldByClass(EntityField.class);
  }

  public FileField getFileField() {
    return getFieldByClass(FileField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  @ClassId("15233e7e-0fc0-4205-8506-194be9f65b26")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("a3c16037-f74e-491a-946b-bd2d211a6280")
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("af44275b-034e-4fb6-b05f-517d9687bb24")
      public class EntityField extends AbstractSmartField<Class<? extends AbstractForm>> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Entity");
        }

        @Override
        protected Class<? extends ILookupCall<Class<? extends AbstractForm>>> getConfiguredLookupCall() {
          return ImportableEntityFormLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      @ClassId("dc51f961-6273-4129-8200-ad52fa618318")
      public class FileField extends AbstractFileChooserField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("File");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return PreparationForm.MainBox.GroupBox.EntityField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @Override
        protected List<String> getConfiguredFileExtensions() {
          return CollectionUtility.arrayList("csv");
        }

        @Override
        protected void execChangedValue() {
          if (getWizard() != null) {
            if (getValue() != null) {
              String fileName = getValue().getFilename();
              if (StringUtility.hasText(fileName)) {
                String[] nameParts = StringUtility.split(fileName, "\\.(?=[^\\.]+$)");
                String extension = StringUtility.lowercase(CollectionUtility.lastElement(CollectionUtility.arrayList(nameParts)));
                switch (extension) {
                  case "csv":
                    getWizard().setFileHelper(new CsvFileHelper(getValue()));
                    break;
                  default:
                    throw new VetoException(TEXTS.get("TheFileExtensionIsNotSupported"));
                }
              }
            }
            else {
              getWizard().setFileHelper(null);
            }
          }
          getColumnMappingField().reloadTableData();
        }
      }

      @Order(30.0)
      @ClassId("9c02d5c8-4d77-4e8c-be40-418014613175")
      public class ColumnMappingField extends AbstractTableField<ColumnMappingField.Table> {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ColumnMapping");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return PreparationForm.MainBox.GroupBox.EntityField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @SuppressWarnings("unchecked")
        public Map<String, Class<? extends AbstractValueFieldData>> getTableContentAsMap() {
          Map<String, Class<? extends AbstractValueFieldData>> resultMap = new HashMap<>();
          for (int i = 0; i < getTable().getRowCount(); i++) {
            String headerName = TypeCastUtility.castValue(getTable().getTableData()[i][0], String.class);
            Class<? extends AbstractValueFieldData> fieldClass = (Class<? extends AbstractValueFieldData>) getTable().getTableData()[i][1];
            resultMap.put(headerName, fieldClass);
          }
          return resultMap;
        }

        @Override
        public void reloadTableData() {
          // clear content
          getTable().deleteAllRows();

          // populate
          if (getWizard().getFileHelper() != null) {
            @SuppressWarnings("unchecked")
            Class<? extends AbstractFormData> formDataType = getEntityField().getValue().getAnnotation(FormData.class).value();
            for (String headerName : getWizard().getFileHelper().getColumnNames()) {
              ITableRow row = getTable().createRow();
              getTable().getSourceColumn().setValue(row, headerName);
              if (StringUtility.hasText(headerName)) {
                ILookupCall<Class<? extends AbstractValueFieldData<?>>> call = getTable().getTargetFieldColumn().getLookupCall();
                call.setText(headerName);
                call.setMaster(formDataType);
                List<? extends ILookupRow<Class<? extends AbstractValueFieldData<?>>>> dataByText = call.getDataByText();
                if (CollectionUtility.size(dataByText) == 1) {
                  getTable().getTargetFieldColumn().setValue(row, CollectionUtility.firstElement(dataByText).getKey());
                }
              }
              getTable().addRow(row, true);
            }
          }
        }

        @Order(10.0)
        @ClassId("788fb837-8b20-4043-abf1-8b1a2e2b1258")
        public class Table extends AbstractTable {

          public SourceColumn getSourceColumn() {
            return getColumnSet().getColumnByClass(SourceColumn.class);
          }

          public TargetFieldColumn getTargetFieldColumn() {
            return getColumnSet().getColumnByClass(TargetFieldColumn.class);
          }

          @Order(10.0)
          @ClassId("2100a80b-e642-4416-8e39-8748806bb4cc")
          public class SourceColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("SourceColumn");
            }

            @Override
            protected int getConfiguredWidth() {
              return 200;
            }
          }

          @Order(20.0)
          @ClassId("e0b97e9e-27e0-4714-83a0-307c2b2e7f97")
          public class TargetFieldColumn extends AbstractSmartColumn<Class<? extends AbstractValueFieldData<?>>> {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("TargetField");
            }

            @Override
            protected Class<? extends ILookupCall<Class<? extends AbstractValueFieldData<?>>>> getConfiguredLookupCall() {
              return FormDataFieldLookupCall.class;
            }

            @Override
            protected int getConfiguredWidth() {
              return 200;
            }

            @Override
            protected void execPrepareLookup(ILookupCall<Class<? extends AbstractValueFieldData<?>>> call, ITableRow row) {
              call.setMaster(getEntityField().getValue().getAnnotation(FormData.class).value());
            }
          }
        }
      }
    }

    @Order(20.0)
    @ClassId("9ecd6a2d-b18e-4ec2-b28e-cb83d44911c1")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("a71971f7-bdc6-42fc-8a12-6ea2581ce30f")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class PreparationHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      Map<String, Class<? extends AbstractValueFieldData>> columnMapping = getColumnMappingField().getTableContentAsMap();
      if (CollectionUtility.isEmpty(CollectionUtility.hashSetWithoutNullElements(columnMapping.values()))) {
        throw new VetoException(TEXTS.get("NoColumnsMapped"));
      }
      if (getWizard() != null) {
        getWizard().setColumnMapping(columnMapping);
        getWizard().setFormType(getEntityField().getValue());
      }
    }
  }
}

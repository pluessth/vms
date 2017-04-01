package ch.pproject.vms.client.core.csv;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTableRowBuilder;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.ParsingFailedStatus;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ColorUtility;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.TypeCastUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.CancelButton;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.GroupBox;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.GroupBox.DeselectAllButton;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.GroupBox.RevalidateTableButton;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.GroupBox.SelectAllButton;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.GroupBox.SelectionField;
import ch.pproject.vms.client.core.csv.SelectionForm.MainBox.OkButton;
import ch.pproject.vms.shared.core.csv.SelectionFormData;
import ch.pproject.vms.shared.core.entityimport.IImportService;
import ch.pproject.vms.shared.core.entityimport.annotation.ImportableEntity;

@ClassId("1f223b5e-fd4a-4e33-87b1-e5087ee1fe12")
@FormData(value = SelectionFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SelectionForm extends AbstractCsvWizardImportForm {

  public SelectionForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Selection");
  }

  public void startSelection() {
    startInternal(new SelectionForm.SelectionHandler());
  }

  public DeselectAllButton getDeselectAllButton() {
    return getFieldByClass(DeselectAllButton.class);
  }

  public SelectAllButton getSelectAllButton() {
    return getFieldByClass(SelectAllButton.class);
  }

  public RevalidateTableButton getRevalidateTableButton() {
    return getFieldByClass(RevalidateTableButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
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

  public SelectionField getSelectionField() {
    return getFieldByClass(SelectionField.class);
  }

  @Order(10.0)
  @ClassId("6fc73e7a-622a-4778-a24c-c40d390df954")
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @ClassId("3b4d6d8f-bb3e-4074-b99e-799b4ee6a861")
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      @ClassId("3c91a721-c4f8-4571-a76b-a2f4f7688f73")
      public class SelectionField extends AbstractTableField<SelectionField.Table> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Selection");
        }

        @Order(10.0)
        @ClassId("bfd19f1a-6753-4240-97f1-3255ea436227")
        public class Table extends AbstractTable {

          private final SimpleDateFormat m_dateFormat;
          List<IColumn<?>> m_injectedColumns;
          Map<Class<? extends AbstractCodeType>, Collection<String>> m_codeTypeMap;

          public Table() {
            m_dateFormat = new SimpleDateFormat("dd.MM.yy");
            getDateFormat().setLenient(false);
          }

          @SuppressWarnings("unchecked")
          public void initializeTable() {
            if (getWizard() != null && getWizard().getFileHelper() != null && getWizard().getColumnMapping() != null) {
              AbstractFileHelper fileHelper = getWizard().getFileHelper();
              if (m_injectedColumns == null) {
                m_injectedColumns = CollectionUtility.emptyArrayList();
              }
              if (m_codeTypeMap == null) {
                m_codeTypeMap = new HashMap<>();
              }
              int i = 100;
              if (CollectionUtility.hasElements(fileHelper.getColumnNames())) {
                for (String headerName : fileHelper.getColumnNames()) {
                  Class<? extends AbstractValueFieldData> validationDataType = getWizard().getColumnMapping().get(headerName);
                  if (validationDataType != null) {
                    // check for codeType
                    try {
                      AbstractForm form = getWizard().getFormType().newInstance();
                      IFormField field = form.getFieldById(validationDataType.getSimpleName() + "Field");
                      if (field == null) {
                        throw new ProcessingException(TEXTS.get("ErrorCreatingDynamicColumn", headerName));
                      }
                      if (field instanceof AbstractSmartField) {
                        Class<? extends AbstractCodeType<Long, Long>> codeTypeClass = ((AbstractSmartField) field).getCodeTypeClass();
                        if (codeTypeClass != null) {
                          List<? extends ICode<Long>> codes = BEANS.get(codeTypeClass).getCodes();
                          Set<String> texts = CollectionUtility.emptyHashSet();
                          for (ICode<Long> code : codes) {
                            texts.add(code.getText());
                          }
                          m_codeTypeMap.put(codeTypeClass, texts);
                          m_injectedColumns.add(createDynamicValidatableSmartLongColumn(String.valueOf(i), headerName, validationDataType, codeTypeClass));
                        }
                        else {
                          IColumn<?> col = createDynamicStringColumn(String.valueOf(i), headerName);
                          col.setEditable(false);
                          m_injectedColumns.add(col);
                        }
                      }
                      else {
                        m_injectedColumns.add(createDynamicValidatableStringColumn(String.valueOf(i), headerName, validationDataType, TypeCastUtility.getGenericsParameterClass(validationDataType, AbstractValueFieldData.class)));
                      }
                    }
                    catch (InstantiationException | IllegalAccessException e) {
                      throw new ProcessingException(TEXTS.get("ErrorCreatingDynamicColumn", headerName), e);
                    }
                  }
                  else {
                    m_injectedColumns.add(createDynamicStringColumn(String.valueOf(i), headerName));
                  }
                  i++;
                }
              }
              getSelectionField().getTable().resetColumnConfiguration();
              getSelectionField().getTable().addRowsByMatrix(fileHelper.getData());
              validateTable();
            }
          }

          @Override
          public List<ITableRow> createRowsByMatrix(Object dataMatrixOrReference, int rowStatus) {
            return new AbstractTableRowBuilder<Object>() {

              @Override
              protected ITableRow createEmptyTableRow() {
                return new TableRow(getColumnSet());
              }

              @Override
              public ITableRow createRow(Object rowValues) {
                if (!rowValues.getClass().isArray()) {
                  throw new IllegalArgumentException("argument must be an array value []");
                }
                ITableRow row = createEmptyTableRow();
                for (int c = 0, nc = Array.getLength(rowValues); c < nc; c++) {
                  if (getColumnSet().getColumn(c) instanceof AbstractSmartColumn && !(rowValues instanceof Long)) {
                    row.getCellForUpdate(c).addErrorStatus(StringUtility.valueOf(Array.get(rowValues, c)));
                  }
                  else {
                    row.setCellValue(c, Array.get(rowValues, c));
                  }
                }
                return row;
              }
            }.createRowsByMatrix(dataMatrixOrReference, rowStatus);
          }

          public SimpleDateFormat getDateFormat() {
            return m_dateFormat;
          }

          public void validateTable() {
            for (IColumn<?> col : getColumns()) {
              if (col instanceof AbstractValidatableStringColumn) {
                AbstractValidatableStringColumn validatableColumn = (AbstractValidatableStringColumn) col;
                for (ITableRow row : getRows()) {
                  validateStringCell(row, validatableColumn, row.getCellForUpdate(validatableColumn).getValue());
                }
              }
            }
          }

          @Override
          protected void injectColumnsInternal(OrderedCollection<IColumn<?>> columnList) {
            if (m_injectedColumns != null) {
              columnList.addAllLast(m_injectedColumns);
            }
          }

          @Override
          protected Class<? extends AbstractBooleanColumn> getConfiguredCheckableColumn() {
            return getSelectionColumn().getClass();
          }

          protected void validateStringCell(ITableRow row, AbstractValidatableStringColumn col, Object value) {
            Class<?> validationDataType = col.getValidationDataType();
            if (row != null && col != null && validationDataType != null) {
              String stringValue = (String) value;
              try {
                try {
                  if (CompareUtility.equals(validationDataType, String.class)) {
                    // do nothing
                  }
                  else if (CompareUtility.equals(validationDataType, Boolean.class)) {
                    Boolean.parseBoolean(stringValue);
                  }
                  else if (CompareUtility.equals(validationDataType, Long.class)) {
                    Long.parseLong(stringValue);
                  }
                  else if (CompareUtility.equals(validationDataType, BigDecimal.class)) {
                    new BigDecimal(stringValue);
                  }
                  else if (CompareUtility.equals(validationDataType, Integer.class)) {
                    Integer.parseInt(stringValue);
                  }
                  else if (CompareUtility.equals(validationDataType, Date.class)) {
                    if (stringValue != null) {
                      getDateFormat().parse(stringValue);
                    }
                  }
                  else {
                    throw new ProcessingException(TEXTS.get("ValidationIsNotImplementedForThisColumnType", StringUtility.nvl(validationDataType, "null")));
                  }
                }
                catch (Exception e) {
                  throw new ProcessingException(TEXTS.get("ValueWithWrongFormat"), e);
                }
                row.getCellForUpdate(col).clearErrorStatus();
                row.getCellForUpdate(col).setBackgroundColor(ColorUtility.WHITE);
              }
              catch (ProcessingException e) {
                row.getCellForUpdate(col).addErrorStatus(new ParsingFailedStatus(e, TEXTS.get("ValueWithWrongFormat", StringUtility.valueOf(value))));
                row.getCellForUpdate(col).setBackgroundColor("ffdddd");
              }
            }
          }

          protected IColumn<?> createDynamicValidatableStringColumn(final String columnId, final String label, final Class<? extends AbstractValueFieldData> formFieldType, final Class<?> validationDataType) {
            return new AbstractValidatableStringColumn() {

              @Override
              protected Class<?> getValidationDataType() {
                return validationDataType;
              }

              @Override
              public Class<? extends AbstractValueFieldData> getFormFieldType() {
                return formFieldType;
              }

              @Override
              protected String getConfiguredHeaderText() {
                return label;
              }

              @Override
              public String getColumnId() {
                return columnId;
              }

              @Override
              protected String execValidateValue(ITableRow row, String rawValue) {
                validateStringCell(row, this, rawValue);
                return rawValue;
              }

            };
          }

          protected IColumn<?> createDynamicValidatableSmartLongColumn(final String columnId, final String label, final Class<? extends AbstractValueFieldData> validationDataType,
              final Class<? extends AbstractCodeType<Long, Long>> validataionCodeType) {
            return new AbstractValidatableSmartLongColumn() {

              private List<? extends ICode<Long>> m_codes;

              @Override
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return validataionCodeType;
              }

              @Override
              protected String getConfiguredHeaderText() {
                return label;
              }

              @Override
              public String getColumnId() {
                return columnId;
              }

              @Override
              protected void execInitColumn() {
                m_codes = BEANS.get(getConfiguredCodeType()).getCodes();
              }

              @Override
              protected Long execParseValue(ITableRow row, Object rawValue) {
                if (rawValue instanceof Long) {
                  row.getCellForUpdate(this).clearErrorStatus();
                  row.getCellForUpdate(this).setBackgroundColor(ColorUtility.WHITE);
                  return super.execParseValue(row, rawValue);
                }
                String value = StringUtility.valueOf(rawValue);
                for (ICode<Long> code : m_codes) {
                  if (StringUtility.equalsIgnoreCase(value, code.getText())) {
                    row.getCellForUpdate(this).clearErrorStatus();
                    row.getCellForUpdate(this).setBackgroundColor(ColorUtility.WHITE);
                    return super.execParseValue(row, code.getId());
                  }
                }
                if (StringUtility.hasText(value)) {
                  row.getCellForUpdate(this).addErrorStatus(new ParsingFailedStatus(TEXTS.get("NoMatchingValueFoundInList"), value));
                  row.getCellForUpdate(this).setBackgroundColor("ffdddd");
                }
                return super.execParseValue(row, null);
              }

              @Override
              public Class<? extends AbstractValueFieldData> getFormFieldType() {
                return validationDataType;
              }
            };
          }

          protected IColumn<?> createDynamicStringColumn(final String columnId, final String label) {
            return new AbstractStringColumn() {

              @Override
              protected String getConfiguredHeaderText() {
                return label;
              }

              @Override
              public String getColumnId() {
                return columnId;
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }

              @Override
              protected boolean getConfiguredEditable() {
                return false;
              }

              @Override
              public void addPropertyChangeListener(PropertyChangeListener listener) {
                super.addPropertyChangeListener(listener);
              }

              @Override
              protected String getConfiguredBackgroundColor() {
                return "f6f6f6";
              }

              @Override
              protected String getConfiguredForegroundColor() {
                return "666666";
              }
            };
          }

          public SelectionColumn getSelectionColumn() {
            return getColumnSet().getColumnByClass(SelectionColumn.class);
          }

          @Order(10.0)
          @ClassId("e7ff0972-8912-46c2-ae10-eec61a6a291d")
          public class SelectionColumn extends AbstractBooleanColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Selection");
            }

            @Override
            protected double getConfiguredViewOrder() {
              return 0.0;
            }
          }
        }
      }

      @Order(20.0)
      @ClassId("c3e50d53-dcc4-4caa-8e4a-b18fb320b424")
      public class SelectAllButton extends AbstractButton {

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ButtonSelectAll");
        }

        @Override
        protected void execClickAction() {
          for (ITableRow row : getSelectionField().getTable().getRows()) {
            row.setCellValue(0, Boolean.TRUE);
          }
        }
      }

      @Order(30.0)
      @ClassId("a1a97507-57d7-44db-8c06-b7ebd83cf43a")
      public class DeselectAllButton extends AbstractButton {

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ButtonDeselectAll");
        }

        @Override
        protected void execClickAction() {
          for (ITableRow row : getSelectionField().getTable().getRows()) {
            row.setCellValue(0, Boolean.FALSE);
          }
        }
      }

      @Order(30.0)
      @ClassId("d0a23015-365f-4c8f-a3ee-c969fce17de1")
      public class RevalidateTableButton extends AbstractButton {

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ValidateTable");
        }

        @Override
        protected void execClickAction() {
          getSelectionField().getTable().validateTable();
        }
      }
    }

    @Order(20.0)
    @ClassId("48348799-5ba1-44f2-a290-319b9e3b2658")
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    @ClassId("29dcdc24-1223-4336-8b8a-20674023408a")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class SelectionHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      getSelectionField().getTable().initializeTable();
    }

    @Override
    protected void execStore() {
      // check table for fields with error and warn
      boolean hasError = false;
      int showYesNoMessage = 0;
      for (ITableRow row : getSelectionField().getTable().getRows()) {
        if (hasError) {
          showYesNoMessage = new MessageBox().withHeader(TEXTS.get("IncompleteData")).withBody(TEXTS.get("IncompleteDataText") + "\n" + TEXTS.get("Continue") + "?").show();
          break;
        }
        else if (BooleanUtility.nvl(getSelectionField().getTable().getSelectionColumn().getValue(row))) {
          for (int i = 1; i < getSelectionField().getTable().getColumnCount(); i++) {
            if (row.getCell(i).getErrorStatus() != null) {
              break;
            }
          }
        }
      }
      if (showYesNoMessage == IMessageBox.YES_OPTION) {
        Class<? extends AbstractForm> formType = getWizard().getFormType();
        @SuppressWarnings("unchecked")
        Class<? extends AbstractFormData> formDataType = formType.getAnnotation(FormData.class).value();
        ImportableEntity entityAnnotation = formType.getAnnotation(ImportableEntity.class);
        if (entityAnnotation != null) {
          IImportService service = BEANS.get(entityAnnotation.importServiceInterface());
          if (service != null) {
            for (ITableRow row : getSelectionField().getTable().getRows()) {
              if (BooleanUtility.nvl(getSelectionField().getTable().getSelectionColumn().getValue(row))) {
                try {
                  AbstractFormData formData = formDataType.newInstance();
                  fillFormDataFromRow(row, formData);
                  service.storeSilent(formData);
                }
                catch (InstantiationException | IllegalAccessException e) {
                  throw new ProcessingException("Error on creating instance of " + formDataType);
                }
              }
            }
          }
          else {
            throw new ProcessingException("Service not found for " + formDataType + ". Data can not be imported.");
          }
        }
        else {
          throw new ProcessingException("Missing " + ImportableEntity.class.getSimpleName() + " annotation. Data can not be imported.");
        }
      }
    }

  }

  @Override
  public void validateForm() {
    // nop. Validation is done in handler
  }

  @SuppressWarnings("unchecked")
  protected void fillFormDataFromRow(ITableRow row, AbstractFormData formData) {
    for (IColumn<?> column : getSelectionField().getTable().getColumns()) {
      if (column instanceof IValidatableColumn) {
        IValidatableColumn validatableColumn = (IValidatableColumn) column;
        if (row.getCell(column).getErrorStatus() == null) {
          Object value = null;
          if (validatableColumn instanceof AbstractValidatableStringColumn) {
            String cellValue = StringUtility.valueOf(row.getCell(column).getValue());
            AbstractValidatableStringColumn validatableStringColumn = (AbstractValidatableStringColumn) validatableColumn;
            try {
              if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), String.class)) {
                value = cellValue;
              }
              else if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), Boolean.class)) {
                value = Boolean.parseBoolean(cellValue);
              }
              else if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), Long.class)) {
                value = Long.parseLong(cellValue);
              }
              else if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), BigDecimal.class)) {
                value = new BigDecimal(cellValue);
              }
              else if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), Integer.class)) {
                value = Integer.parseInt(cellValue);
              }
              else if (CompareUtility.equals(validatableStringColumn.getValidationDataType(), Date.class)) {
                value = getSelectionField().getTable().getDateFormat().parse(cellValue);
              }
            }
            catch (ParseException e) {
              // XXX tpu check for errorstatus does not work -> parse exception!
              e.printStackTrace();
            }
          }
          else if (validatableColumn instanceof AbstractValidatableSmartLongColumn) {
            AbstractValidatableSmartLongColumn validatableSmartLongColumn = (AbstractValidatableSmartLongColumn) validatableColumn;
            value = row.getCell(validatableSmartLongColumn).getValue();
          }
          formData.getFieldByClass(validatableColumn.getFormFieldType()).setValue(value);
        }
      }
    }
  }
}

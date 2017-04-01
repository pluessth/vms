package ch.pproject.vms.client.core.csv;

import java.io.StringReader;
import java.util.List;

import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.shared.csv.CsvHelper;

public class CsvFileHelper extends AbstractFileHelper {

  private CsvHelper m_csvHelper;
  private SelectionArrayConsumer m_consumer = new SelectionArrayConsumer();

  public CsvFileHelper(BinaryResource file) {
    super(file);
    m_csvHelper = new CsvHelper(null, ";", "\"", "\n");
    m_csvHelper.importData(m_consumer, new StringReader(file.getContentAsString()), true, true, 1, -1, false);
  }

  @Override
  public List<String> getColumnNames() {
    return m_csvHelper.getColumnNames();
  }

  @Override
  public Object[][] getData() {
    return m_consumer.getData();
  }

}

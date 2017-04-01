package ch.pproject.vms.client.core.csv;

import java.util.List;

import org.eclipse.scout.rt.shared.csv.ArrayConsumer;

public class SelectionArrayConsumer extends ArrayConsumer {

  @Override
  public void processRow(int lineNr, List<Object> row) {
    for (Object object : row) {
      if (object != null) {
        row.add(0, Boolean.FALSE);
        super.processRow(lineNr, row);
        break;
      }
    }
  }

}

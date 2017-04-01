package ch.pproject.vms.client.core.csv;

import java.util.List;

public interface IFileHelper {

  List<String> getColumnNames() ;

  Object[][] getData() ;
}

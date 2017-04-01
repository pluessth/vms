package ch.pproject.vms.client.core.csv;

import java.io.File;

import org.eclipse.scout.rt.platform.service.IService;

public interface ICsvFileImportService extends IService {

  void importCsvFile(File file) ;

}

package ch.pproject.vms.client.core.csv;

import org.eclipse.scout.rt.platform.resource.BinaryResource;

public abstract class AbstractFileHelper implements IFileHelper {

  private BinaryResource m_file;

  public AbstractFileHelper(BinaryResource file) {
    setFile(file);
  }

  public BinaryResource getFile() {
    return m_file;
  }

  public void setFile(BinaryResource file) {
    m_file = file;
  }
}

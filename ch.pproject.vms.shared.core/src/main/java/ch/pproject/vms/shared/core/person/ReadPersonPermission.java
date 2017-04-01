package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadPersonPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public ReadPersonPermission() {
    super(ReadPersonPermission.class.getSimpleName());
  }
}

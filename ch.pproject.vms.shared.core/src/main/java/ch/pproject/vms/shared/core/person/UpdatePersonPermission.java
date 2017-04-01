package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class UpdatePersonPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public UpdatePersonPermission() {
    super(UpdatePersonPermission.class.getSimpleName());
  }
}

package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreatePersonPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public CreatePersonPermission() {
    super(CreatePersonPermission.class.getSimpleName());
  }
}

package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreateAccountPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public CreateAccountPermission() {
    super(CreateAccountPermission.class.getSimpleName());
  }
}

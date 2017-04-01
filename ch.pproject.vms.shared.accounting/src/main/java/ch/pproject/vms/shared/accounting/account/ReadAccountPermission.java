package ch.pproject.vms.shared.accounting.account;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadAccountPermission extends AbstractVmsPermission {
  private static final long serialVersionUID = 0L;

  public ReadAccountPermission() {
    super(ReadAccountPermission.class.getSimpleName());
  }
}

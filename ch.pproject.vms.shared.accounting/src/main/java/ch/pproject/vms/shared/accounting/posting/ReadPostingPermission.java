package ch.pproject.vms.shared.accounting.posting;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadPostingPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public ReadPostingPermission() {
    super(ReadPostingPermission.class.getSimpleName());
  }
}

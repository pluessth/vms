package ch.pproject.vms.shared.accounting.posting;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class UpdatePostingPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public UpdatePostingPermission() {
    super(UpdatePostingPermission.class.getSimpleName());
  }
}

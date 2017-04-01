package ch.pproject.vms.shared.accounting.posting;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreatePostingPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public CreatePostingPermission() {
    super(CreatePostingPermission.class.getSimpleName());
  }
}

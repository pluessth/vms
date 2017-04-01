package ch.pproject.vms.shared.core.person;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadMemberOutlinePermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 0L;

  public ReadMemberOutlinePermission() {
    super(ReadMemberOutlinePermission.class.getSimpleName());
  }
}

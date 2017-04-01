package ch.pproject.vms.shared.core.team;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class ReadTeamPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 1L;

  public ReadTeamPermission() {
    super(ReadTeamPermission.class.getSimpleName());
  }
}

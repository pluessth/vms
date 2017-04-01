/**
 *
 */
package ch.pproject.vms.shared.core.team;

import ch.pproject.vms.shared.core.administration.AbstractVmsPermission;

public class CreateTeamPermission extends AbstractVmsPermission {

  private static final long serialVersionUID = 1L;

  public CreateTeamPermission() {
    super(CreateTeamPermission.class.getSimpleName());
  }
}

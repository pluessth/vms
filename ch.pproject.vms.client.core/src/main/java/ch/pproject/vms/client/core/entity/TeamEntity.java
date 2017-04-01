package ch.pproject.vms.client.core.entity;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.vms.client.core.team.TeamForm;

public class TeamEntity extends AbstractEntity {

  @Override
  public String getText() {
    return TEXTS.get("Team");
  }

  @Override
  public Class<? extends AbstractForm> getFormClass() {
    return TeamForm.class;
  }

}

package ch.pproject.vms.client.core.entity;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.vms.client.core.person.PersonForm;

public class PersonEntity extends AbstractEntity {

  @Override
  public String getText() {
    return TEXTS.get("Person");
  }

  @Override
  public Class<? extends AbstractForm> getFormClass() {
    return PersonForm.class;
  }

}

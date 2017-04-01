package ch.pproject.vms.client.accounting.entity;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.vms.client.accounting.posting.PostingForm;
import ch.pproject.vms.client.core.entity.AbstractEntity;

public class PostingEntity extends AbstractEntity {

  @Override
  public String getText() {
    return TEXTS.get("Posting");
  }

  @Override
  public Class<? extends AbstractForm> getFormClass() {
    return PostingForm.class;
  }

}

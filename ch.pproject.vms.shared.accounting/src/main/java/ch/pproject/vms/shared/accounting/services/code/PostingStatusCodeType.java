package ch.pproject.vms.shared.accounting.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("f685449a-6af5-44d6-b7a1-087a831ae3f7")
public class PostingStatusCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 520000L;

  public PostingStatusCodeType() {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Status");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  @ClassId("7daa1974-3acb-476d-9bec-b22ed83dbe5d")
  public static class OpenStatusCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 520001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("OpenStatus");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  @ClassId("5275faf5-5946-4c72-a457-6e0c2baea52b")
  public static class EnteredStatusCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 520002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("EnteredStatus");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}

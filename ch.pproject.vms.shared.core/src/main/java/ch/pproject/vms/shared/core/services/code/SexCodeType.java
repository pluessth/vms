package ch.pproject.vms.shared.core.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("c82e6de3-3230-4c4f-ba79-154fdc45604b")
public class SexCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 20000L;

  public SexCodeType() {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Sex");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  @ClassId("26d0eb63-d37c-4cd2-b5eb-18b71f54f0a8")
  public static class MsCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Ms");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  @ClassId("a35374f0-bede-4d61-a322-bcfd92faaacc")
  public static class MrCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 20002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Mr");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}

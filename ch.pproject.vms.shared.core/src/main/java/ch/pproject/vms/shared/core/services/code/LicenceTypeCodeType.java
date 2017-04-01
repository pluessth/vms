package ch.pproject.vms.shared.core.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("6fa79e38-ffb6-41a7-9de1-719a0739038c")
public class LicenceTypeCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 30000L;

  public LicenceTypeCodeType()  {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("LicenceType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  @ClassId("2dc6356d-4ea4-40d6-9f0a-5411583c1cb1")
  public static class LargeFieldCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 30001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("LargeField");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  @ClassId("6e90218d-2ddf-49d4-a1fd-ba81a22082aa")
  public static class SmallFieldCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 30002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("SmallField");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
  @ClassId("fa710c0b-b297-4d80-be75-513ec589dfb3")
  public static class JuniorsCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 30003L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Juniors");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}

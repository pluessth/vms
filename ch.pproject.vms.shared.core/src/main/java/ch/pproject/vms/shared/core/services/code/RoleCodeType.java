package ch.pproject.vms.shared.core.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("297570cb-8154-436d-ba37-2d9d022720c1")
public class RoleCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  public RoleCodeType()  {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Role");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  @ClassId("4374dc7d-bcb8-4c26-afc1-f9f093188eb0")
  public static class ActiveMemberCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ActiveMember");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  @ClassId("966df3ff-39ef-4ec7-845d-329bb75bb2b9")
  public static class PassiveMemberCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PassiveMember");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
  @ClassId("3459a8be-4cf8-4a71-8259-b8e9c4d9dcbc")
  public static class JuniorCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10003L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Junior");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(40.0)
  @ClassId("dd761f5b-207b-4efa-a157-4aecca79a258")
  public static class ChairmanCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10004L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Chairman");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(50.0)
  @ClassId("4cc0205e-8ffe-4f59-af1f-11cc7c3ef6cd")
  public static class RefereeCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10005L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Referee");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(60.0)
  @ClassId("ae872cb8-dff0-482d-91f3-c03974253d65")
  public static class CoachCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10006L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Coach");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(70.0)
  @ClassId("58a78424-98ac-417c-8ffd-0f99bb267ab2")
  public static class HonoraryMemberCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10007L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("HonoraryMember");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}

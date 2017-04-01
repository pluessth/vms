package ch.pproject.vms.shared.accounting.account;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("73ddfc98-712a-4fd4-af51-d58873331baf")
public class AccountTypeCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 510000L;

  public AccountTypeCodeType() {
    super();
  }

  @Override
  protected boolean getConfiguredIsHierarchy() {
    return true;
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("AccountType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  @ClassId("b6f79c2c-e551-46fd-9699-73edd8a93d13")
  public static class InventoryAccountCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 510001L;

    @Override
    protected boolean getConfiguredEnabled() {
      return false;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("InventoryAccount");
    }

    @Override
    public Long getId() {
      return ID;
    }

    @Order(10.0)
    @ClassId("acdc720c-ec60-48ad-b62d-bc6afcb1edb1")
    public static class AssetAccountCode extends AbstractCode<Long> {

      private static final long serialVersionUID = 1L;
      public static final Long ID = 510002L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AssetAccount");
      }

      @Override
      public Long getId() {
        return ID;
      }
    }

    @Order(20.0)
    @ClassId("357e4893-16a4-4ab0-8ad8-fdaf1582f6ce")
    public static class LiabilityAccountCode extends AbstractCode<Long> {

      private static final long serialVersionUID = 1L;
      public static final Long ID = 510003L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("LiabilityAccount");
      }

      @Override
      public Long getId() {
        return ID;
      }
    }
  }

  @Order(20.0)
  @ClassId("48d2b9d2-41a6-4653-9a34-be3ac31b7858")
  public static class NominalAccountCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 510010L;

    @Override
    protected boolean getConfiguredEnabled() {
      return false;
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("NominalAccount");
    }

    @Override
    public Long getId() {
      return ID;
    }

    @Order(10.0)
    @ClassId("d35a815a-5dff-4a2d-864e-3e26935c334d")
    public static class IncomeAccountCode extends AbstractCode<Long> {

      private static final long serialVersionUID = 1L;
      public static final Long ID = 510011L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("IncomeAccount");
      }

      @Override
      public Long getId() {
        return ID;
      }
    }

    @Order(20.0)
    @ClassId("2444c963-d013-4ec4-a243-c3c5f42a0325")
    public static class ExpenseAccountCode extends AbstractCode<Long> {

      private static final long serialVersionUID = 1L;
      public static final Long ID = 510012L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ExpenseAccount");
      }

      @Override
      public Long getId() {
        return ID;
      }
    }
  }
}

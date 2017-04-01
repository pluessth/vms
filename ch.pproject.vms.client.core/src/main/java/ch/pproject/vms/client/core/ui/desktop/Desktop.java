package ch.pproject.vms.client.core.ui.desktop;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.useradmin.DefaultPasswordForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.client.core.ClientSession;
import ch.pproject.vms.client.core.csv.CsvImportWizard;
import ch.pproject.vms.client.core.ui.desktop.outlines.ActivityOutline;
import ch.pproject.vms.client.core.ui.desktop.outlines.AdministrationOutline;
import ch.pproject.vms.client.core.ui.desktop.outlines.MemberOutline;
import ch.pproject.vms.shared.core.Icons;
import ch.pproject.vms.shared.core.desktop.ReadToolsMenuPermission;

public class Desktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(
        MemberOutline.class,
        ActivityOutline.class,
        AdministrationOutline.class);
  }

  @Override
  protected void execGuiAttached() {
    super.execGuiAttached();
    selectFirstVisibleOutline();
  }

  protected void selectFirstVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline.isEnabled() && outline.isVisible()) {
        setOutline(outline);
        break;
      }
    }
  }

  @Order(500)
  @ClassId("e83fc32c-41e3-4f77-a7d8-591a758fae95")
  public class ToolsMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ToolsMenu");
    }

    @Override
    protected boolean getConfiguredVisible() {
      return ACCESS.check(new ReadToolsMenuPermission());
    }

    @Order(10.0)
    @ClassId("56115dc6-b8ba-477b-b063-f257f7f19702")
    public class ImportExportMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ImportExport");
      }

      @Order(10.0)
      @ClassId("5b2eacf6-6157-4fe4-a1c8-23fe0b69ca6c")
      public class ImportPersonsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("CSVImport");
        }

        @Override
        protected void execAction() {
          new CsvImportWizard().start();
        }
      }
    }
  }

  @Order(1000)
  public class UserMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Person;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet();
    }

    @Order(500)
    public class ChangePasswordMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChangePassword");
      }

      @Override
      protected void execAction() {
        DefaultPasswordForm form = new DefaultPasswordForm();
        form.setUserId(ClientSession.get().getUserId());
        form.startChange();
        form.waitFor();
      }
    }

    @Order(1000)
    public class LogoffMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logoff");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }

  }

  @Order(10.0)
  @ClassId("95b8d065-88c7-4cec-8a4e-7221f17e79d5")
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "f5";
    }

    @Override
    protected void execAction() {
      if (getOutline() != null) {
        IPage page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }

  @Order(1000)
  @ClassId("54feca74-9df5-4374-9f4b-ef90263cfdef")
  public class MemberOutlineViewButton extends AbstractOutlineViewButton {

    public MemberOutlineViewButton() {
      this(MemberOutline.class);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    protected MemberOutlineViewButton(Class<? extends MemberOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }
  }

  @Order(2000)
  @ClassId("df7b61d9-f51d-4947-bb06-c1a6b69a481e")
  public class ActivityOutlineViewButton extends AbstractOutlineViewButton {

    public ActivityOutlineViewButton() {
      this(ActivityOutline.class);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    protected ActivityOutlineViewButton(Class<? extends ActivityOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }
  }

  @Order(3000)
  @ClassId("3eda9459-f2c7-42dc-938f-cfd864c16fb8")
  public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {

    public AdministrationOutlineViewButton() {
      this(AdministrationOutline.class);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    protected AdministrationOutlineViewButton(Class<? extends AdministrationOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }
  }

}

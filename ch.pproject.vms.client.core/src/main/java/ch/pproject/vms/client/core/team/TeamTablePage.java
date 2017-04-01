package ch.pproject.vms.client.core.team;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.vms.client.core.team.TeamTablePage.Table;
import ch.pproject.vms.shared.core.services.IMemberOutlineService;
import ch.pproject.vms.shared.core.services.code.LicenceTypeCodeType;
import ch.pproject.vms.shared.core.team.ITeamService;
import ch.pproject.vms.shared.core.team.TeamTablePageData;

@PageData(TeamTablePageData.class)
@ClassId("fc53cbf8-8a1a-4efd-be0e-2612a7fa27d7")
public class TeamTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Teams");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IMemberOutlineService.class).getTeamTableData(filter));
  }

  @Order(10.0)
  @ClassId("dc8d65e2-8875-4554-a628-3e0311cf1c4e")
  public class Table extends AbstractTable {

    public LicenceTypeColumn getLicenceTypeColumn() {
      return getColumnSet().getColumnByClass(LicenceTypeColumn.class);
    }

    public TeamMembersColumn getTeamMembersColumn() {
      return getColumnSet().getColumnByClass(TeamMembersColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public TeamNrColumn getTeamNrColumn() {
      return getColumnSet().getColumnByClass(TeamNrColumn.class);
    }

    @Order(10.0)
    @ClassId("d4e79657-3079-4d6b-8245-ebde97fdb293")
    public class TeamNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(20.0)
    @ClassId("5cbc4791-5fad-4137-877c-f41da688f536")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(30.0)
    @ClassId("05c13086-fe77-4f89-a0d5-a35b3eb20581")
    public class LicenceTypeColumn extends AbstractSmartColumn<Long> {

      @Override
      protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
        return LicenceTypeCodeType.class;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("LicenceType");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(2000)
    public class TeamMembersColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("TeamMembers");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }

      @Override
      protected boolean getConfiguredTextWrap() {
        return true;
      }
    }

    @Order(10.0)
    @ClassId("77a8524f-a515-4e29-a262-960d96a2cf77")
    public class NewTeamMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewTeam");
      }

      @Override
      protected void execAction() {
        TeamForm form = new TeamForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    @ClassId("20e0f6a0-a3fe-427e-9f2c-7e7084199812")
    public class EditTeamMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditTeam");
      }

      @Override
      protected void execAction() {
        TeamForm form = new TeamForm();
        form.setTeamNr(getTeamNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(30)
    public class DeleteTeamMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteTeam");
      }

      @Override
      protected void execAction() {
        if (MessageBoxes.createYesNo().withBody(TEXTS.get("DeleteTeamQuestion")).show() == MessageBox.YES_OPTION) {
          BEANS.get(ITeamService.class).deleteTeam(getTeamNrColumn().getSelectedValue());
          reloadPage();
        }
      }
    }

  }
}

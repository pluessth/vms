package ch.pproject.vms.client.core.person;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import ch.pproject.vms.client.core.entity.PersonEntity;
import ch.pproject.vms.shared.core.person.AbstractPersonTablePageData;
import ch.pproject.vms.shared.core.services.code.SexCodeType;

@PageData(AbstractPersonTablePageData.class)
@ClassId("a6e19751-3367-4fd4-b1a1-8182e576fd39")
public abstract class AbstractPersonTablePage extends AbstractPageWithTable<AbstractPersonTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AllPersons");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  public void initPage() {
    super.initPage();
    registerDataChangeListener(PersonEntity.class);
  }

  @Order(10.0)
  @ClassId("ba7d9677-ef17-45d3-a2c7-cd26297bacdf")
  public class Table extends AbstractTable {

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public StreetColumn getStreetColumn() {
      return getColumnSet().getColumnByClass(StreetColumn.class);
    }

    public ZipColumn getZipColumn() {
      return getColumnSet().getColumnByClass(ZipColumn.class);
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public PhonePrivateColumn getPhonePrivateColumn() {
      return getColumnSet().getColumnByClass(PhonePrivateColumn.class);
    }

    public PhoneMobileColumn getPhoneMobileColumn() {
      return getColumnSet().getColumnByClass(PhoneMobileColumn.class);
    }

    public PhoneWorkColumn getPhoneWorkColumn() {
      return getColumnSet().getColumnByClass(PhoneWorkColumn.class);
    }

    public SexColumn getSexColumn() {
      return getColumnSet().getColumnByClass(SexColumn.class);
    }

    public DateOfBirthColumn getDateOfBirthColumn() {
      return getColumnSet().getColumnByClass(DateOfBirthColumn.class);
    }

    public ContactByEmailColumn getContactByEmailColumn() {
      return getColumnSet().getColumnByClass(ContactByEmailColumn.class);
    }

    public EMailAddressColumn getEMailAddressColumn() {
      return getColumnSet().getColumnByClass(EMailAddressColumn.class);
    }

    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    @Order(10.0)
    @ClassId("bba65e0e-1a57-456f-b5d0-e790a66b7ec7")
    public class IdColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(20.0)
    @ClassId("b5f29151-8a37-4360-ac00-b2c3decc475c")
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(30.0)
    @ClassId("b1dc4ae7-2649-4a46-9d50-707f2bad48fe")
    public class FirstNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(40.0)
    @ClassId("85b94ac4-2c26-4379-a8fe-ae4de9ae1ae0")
    public class SexColumn extends AbstractSmartColumn<Long> {

      @Override
      protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
        return SexCodeType.class;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Sex");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(50.0)
    @ClassId("cb5cbf8e-bcdb-48b7-be27-cdf9be199294")
    public class DateOfBirthColumn extends AbstractDateColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DateOfBirth");
      }

      @Override
      protected int getConfiguredWidth() {
        return 110;
      }
    }

    @Order(60.0)
    @ClassId("22293614-b694-4799-9d9f-06743728b984")
    public class StreetColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Street");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(70.0)
    @ClassId("09a3ebca-d2c2-4404-802f-1e0b14e706cf")
    public class ZipColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ZIP");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(80.0)
    @ClassId("52059364-5a9a-4138-8033-833fd725b4f7")
    public class CityColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("City");
      }
    }

    @Order(90.0)
    @ClassId("c19a2315-2e5e-4884-8a0a-c7771b90c63c")
    public class PhonePrivateColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PhonePrivate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(100.0)
    @ClassId("a1df3e3a-a017-49a3-a345-da351a3153f1")
    public class PhoneMobileColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PhoneMobile");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(110.0)
    @ClassId("c16cc877-833b-4dc3-9344-9432e3d3f039")
    public class PhoneWorkColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PhoneWork");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(120.0)
    @ClassId("285d6ad6-44d1-4c36-b807-f2cad62fa1f5")
    public class EMailAddressColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("EMailAddress");
      }

      @Override
      protected int getConfiguredWidth() {
        return 180;
      }
    }

    @Order(130.0)
    @ClassId("e1cffaca-0e1d-4932-8d7d-0c6ca1e10f65")
    public class ContactByEmailColumn extends AbstractBooleanColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ContactByEmail");
      }

      @Override
      protected int getConfiguredWidth() {
        return 175;
      }
    }

    @Order(10.0)
    @ClassId("892c02b1-89b9-4a12-b70f-d408fc79ee22")
    public class NewPersonMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewPerson");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.Null, CalendarMenuType.EmptySpace, TableMenuType.EmptySpace, TreeMenuType.EmptySpace);
      }

      @Override
      protected void execAction() {
        PersonForm form = new PersonForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          IDesktop.CURRENT.get().dataChanged(PersonEntity.class);
        }
      }
    }

    @Order(20.0)
    @ClassId("b06b8d2a-606b-491d-9b10-7fba65d3801d")
    public class EditPersonMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditPerson");
      }

      @Override
      protected void execAction() {
        PersonForm form = new PersonForm();
        form.setPersonNr(getIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          IDesktop.CURRENT.get().dataChanged(PersonEntity.class);
        }
      }
    }
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return PersonSearchForm.class;
  }
}

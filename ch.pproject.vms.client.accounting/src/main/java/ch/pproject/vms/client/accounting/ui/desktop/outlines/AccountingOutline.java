package ch.pproject.vms.client.accounting.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import ch.pproject.vms.client.accounting.account.AccountingYearTablePage;
import ch.pproject.vms.client.accounting.posting.PostingsTablePage;
import ch.pproject.vms.shared.accounting.Icons;
import ch.pproject.vms.shared.accounting.account.ReadAccountingOutlinePermission;

@ClassId("25a1f817-8a5e-4c6b-9697-544085a4760b")
public class AccountingOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Accounting");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    PostingsTablePage entriesTablePage = new PostingsTablePage() {
      @Override
      protected String getConfiguredTitle() {
        return TEXTS.get("AllPostings");
      }
    };
    pageList.add(entriesTablePage);

    AccountingYearTablePage accountigYearTablePage = new AccountingYearTablePage();
    pageList.add(accountigYearTablePage);
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.LineChart;
  }

  @Override
  protected boolean getConfiguredVisible() {
    return ACCESS.check(new ReadAccountingOutlinePermission());
  }
}

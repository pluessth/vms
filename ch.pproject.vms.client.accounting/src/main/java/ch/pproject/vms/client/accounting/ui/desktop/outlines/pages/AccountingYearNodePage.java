/**
 *
 */
package ch.pproject.vms.client.accounting.ui.desktop.outlines.pages;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.vms.client.accounting.account.AccountsTablePage;
import ch.pproject.vms.client.accounting.posting.PostingsTablePage;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("0ba6ff9a-373f-4ecb-9851-e80debcba90a")
public class AccountingYearNodePage extends AbstractPageWithNodes {

  private Long m_accountingYearNr;

  public Long getAccountingYearNr() {
    return m_accountingYearNr;
  }

  public void setAccountingYearNr(Long accountingYearNr) {
    m_accountingYearNr = accountingYearNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AccountingYear");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    AccountsTablePage accountsPage = new AccountsTablePage();
    accountsPage.setAccountingYearNr(getAccountingYearNr());
    pageList.add(accountsPage);

    PostingsTablePage postingsPage = new PostingsTablePage();
    postingsPage.setAccountingYearNr(getAccountingYearNr());
    pageList.add(postingsPage);
  }

}

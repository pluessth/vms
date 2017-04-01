package ch.pproject.vms.server.core.services.common.sql;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.server.jdbc.mysql.AbstractMySqlSqlService;

public class MySQLSqlService extends AbstractMySqlSqlService implements IService {

  @Override
  public Long getSequenceNextval(String sequenceName) {
    String s = "SELECT nextval('" + sequenceName + "') FROM DUAL ";
    Object[][] ret = createStatementProcessor(s, null, 0).processSelect(getTransaction(), getStatementCache(),
        null);
    if (ret.length == 1) {
      return NumberUtility.toLong(NumberUtility.nvl((Number) ret[0][0], 0));
    }
    return 0L;
  }
}

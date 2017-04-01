package ch.pproject.vms.server.core.administration;

import java.util.Set;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.pproject.vms.server.core.security.ServerAccessControlService;
import ch.pproject.vms.server.core.security.VmsSecurityUtility;
import ch.pproject.vms.server.core.services.common.sql.ISequences;
import ch.pproject.vms.shared.core.administration.CreateUserPermission;
import ch.pproject.vms.shared.core.administration.IUserService;
import ch.pproject.vms.shared.core.administration.ReadUserPermission;
import ch.pproject.vms.shared.core.administration.UpdateUserPermission;
import ch.pproject.vms.shared.core.administration.UserFormData;

public class UserService implements IUserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Override
  public UserFormData prepareCreate(UserFormData formData) {
    if (!ACCESS.check(new CreateUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public UserFormData create(UserFormData formData) {
    if (!ACCESS.check(new CreateUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData.setUserNr(SQL.getSequenceNextval(ISequences.VMSSEQ));
    String password = VmsSecurityUtility.createPasswordHash(formData.getPassword().getValue());

    SQL.insert("" +
        "INSERT INTO USER (" +
        " USER_NR, " +
        " USERNAME, " +
        " PASSWORD " +
        ") VALUES (" +
        " :userNr, " +
        " :userName, " +
        " :pw ) ",
        formData,
        new NVPair("pw", password));
    storePermissions(formData);
    return formData;
  }

  protected void storePermissions(UserFormData formData) {
    Set<String> permissionNames = CollectionUtility.emptyHashSet();
    for (Class c : CollectionUtility.hashSetWithoutNullElements(formData.getPermissionsBox().getValue())) {
      permissionNames.add(c.getName());
    }

    SQL.delete("" +
        "DELETE FROM USER_PERMISSION " +
        "WHERE USER_NR = :userNr ",
        formData);

    if (!permissionNames.isEmpty()) {
      SQL.insert("" +
          "INSERT INTO USER_PERMISSION (USER_NR, PERMISSION_NAME) " +
          "VALUES (:userNr, :{permissions})",
          formData,
          new NVPair("permissions", permissionNames));
    }

    // reload permissons
    BEANS.get(ServerAccessControlService.class).reloadCache(formData.getUserName().getValue());
  }

  protected void loadPermissions(UserFormData formData) {
    formData.getPermissionsBox().setValue(getPermissionsForUser(formData.getUserNr()));
  }

  @Override
  public Set<Class> getPermissionsForUser(String username) {
    LongHolder userNr = new LongHolder();
    SQL.selectInto("" +
        "SELECT " +
        " USER_NR " +
        "FROM USER " +
        "WHERE USERNAME = :username " +
        "INTO :userNr ",
        new NVPair("username", username),
        new NVPair("userNr", userNr));
    return getPermissionsForUser(userNr.getValue());
  }

  @Override
  public Set<Class> getPermissionsForUser(Long userNr) {
    StringArrayHolder permissionHolder = new StringArrayHolder();

    SQL.selectInto("" +
        "SELECT " +
        " PERMISSION_NAME " +
        "FROM USER_PERMISSION " +
        "WHERE USER_NR = :userNr " +
        "INTO :{permissions} ",
        new NVPair("userNr", userNr),
        new NVPair("permissions", permissionHolder));

    Set<String> permissionNames = CollectionUtility.hashSet(permissionHolder.getValue());
    Set<Class> permissionClasses = CollectionUtility.emptyHashSet();
    for (String permissionName : permissionNames) {
      try {
        permissionClasses.add(Class.forName(permissionName));
      }
      catch (ClassNotFoundException e) {
        LOG.error("Unknown permission name:" + permissionName, e);
      }
    }
    return permissionClasses;
  }

  @Override
  public UserFormData load(UserFormData formData) {
    if (!ACCESS.check(new ReadUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT " +
        " USERNAME " +
        "FROM USER " +
        "WHERE USER_NR = :userNr " +
        "INTO :userName ",
        formData);
    loadPermissions(formData);
    return formData;
  }

  @Override
  public UserFormData store(UserFormData formData) {
    if (!ACCESS.check(new UpdateUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    storePermissions(formData);
    return formData;
  }
}

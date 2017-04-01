package ch.pproject.vms.server.core.security;

import java.security.AllPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.Collections;
import java.util.Set;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.shared.cache.KeyCacheEntryFilter;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.pproject.vms.shared.core.administration.IUserService;
import ch.pproject.vms.shared.core.security.AccessControlService;

@Replace
public class ServerAccessControlService extends AccessControlService {

  private static final Logger LOG = LoggerFactory.getLogger(ServerAccessControlService.class);

  @Override
  protected Permissions execLoadPermissions(String userId) {
    Permissions permissions = new Permissions();
    permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));

    if ("admin".equals(userId)) {
      permissions.add(new AllPermission());
      return permissions;
    }

    Set<Class> permissionsForUser = BEANS.get(IUserService.class).getPermissionsForUser(userId);
    for (Class permissionClass : permissionsForUser) {
      Object permission;
      try {
        permission = permissionClass.newInstance();
        if (permission instanceof Permission) {
          permissions.add((Permission) permission);
        }
      }
      catch (InstantiationException | IllegalAccessException e) {
        LOG.error("Could not instantiate permission:" + permissionClass.getName(), e);
      }
    }
    return permissions;
  }

  public void reloadCache(String userId) {
    getCache().invalidate(new KeyCacheEntryFilter<String, PermissionCollection>(Collections.singletonList(userId)), true);
  }
}

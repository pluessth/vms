package ch.pproject.vms.shared.core.administration;

import java.util.Set;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

/**
 *
 */
@TunnelToServer
public interface IUserService extends IService {

  /**
   * @param formData
   * @return
   */
  UserFormData prepareCreate(UserFormData formData);

  /**
   * @param formData
   * @return
   */
  UserFormData create(UserFormData formData);

  /**
   * @param formData
   * @return
   */
  UserFormData load(UserFormData formData);

  /**
   * @param formData
   * @return
   */
  UserFormData store(UserFormData formData);

  /**
   * @param username
   * @return
   */
  Set<Class> getPermissionsForUser(String username);

  /**
   * @param userNr
   * @return
   */
  Set<Class> getPermissionsForUser(Long userNr);
}

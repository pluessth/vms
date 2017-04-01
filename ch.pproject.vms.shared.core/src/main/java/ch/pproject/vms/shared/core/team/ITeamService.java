/**
 *
 */
package ch.pproject.vms.shared.core.team;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITeamService extends IService {

  TeamFormData create(TeamFormData formData);

  TeamFormData load(TeamFormData formData);

  TeamFormData prepareCreate(TeamFormData formData);

  TeamFormData store(TeamFormData formData);

  TeamMemberFormData createTeamMember(TeamMemberFormData formData);

  TeamMemberFormData storeTeamMember(TeamMemberFormData formData);

  TeamMemberFormData deleteTeamMember(TeamMemberFormData formData);

  TeamMemberFormData loadTeamMember(TeamMemberFormData formData);

  void removePersonsFromTeam(Long teamNr, List<Long> personNrs);

  void deleteTeam(Long teamNr);

}

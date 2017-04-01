package ch.pproject.vms.shared.core.person;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import ch.pproject.vms.shared.core.entityimport.IImportService;

@TunnelToServer
public interface IPersonService extends IService, IImportService {

	PersonFormData prepareCreate(PersonFormData formData) ;

	PersonFormData create(PersonFormData formData) ;

	PersonFormData load(PersonFormData formData) ;

	PersonFormData store(PersonFormData formData) ;
}

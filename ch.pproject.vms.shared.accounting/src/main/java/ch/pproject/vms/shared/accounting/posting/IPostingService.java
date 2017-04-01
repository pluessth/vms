package ch.pproject.vms.shared.accounting.posting;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import ch.pproject.vms.shared.core.entityimport.IImportService;

@TunnelToServer
public interface IPostingService extends IService, IImportService {

  PostingFormData prepareCreate(PostingFormData formData);

  PostingFormData create(PostingFormData formData);

  PostingFormData load(PostingFormData formData);

  PostingFormData store(PostingFormData formData);
}

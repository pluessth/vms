package ch.pproject.vms.shared.core.entityimport.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.pproject.vms.shared.core.entityimport.IImportService;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ImportableEntity {

  Class<? extends IImportService> importServiceInterface();

}

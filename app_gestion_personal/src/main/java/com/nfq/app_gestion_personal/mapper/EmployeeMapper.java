package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;
import com.nfq.app_gestion_personal.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interfaz de Mapeo.
 * MapStruct generará una clase que implemente esta interfaz en tiempo de compilación.
 * * componentModel = "spring": Le dice a MapStruct que cree la implementación como un @Component de Spring.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Convierte de DTO de entrada a Entidad de base de datos.
     * * @Mapping(target = "id", ignore = true): Ignora el campo 'id' porque el InputDto no lo tiene y la Entidad lo generará sola.
     */
    @Mapping(target = "projectIds", source = "projectIds")
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeInputDto inputDto);

    /**
     * Convierte de Entidad de base de datos a DTO de salida.
     * MapStruct empareja automáticamente los campos que se llamen igual (name -> name, etc.)
     */
    @Mapping(target = "projects", ignore = true)
    EmployeeOutputDto toOutput(Employee entity);
}
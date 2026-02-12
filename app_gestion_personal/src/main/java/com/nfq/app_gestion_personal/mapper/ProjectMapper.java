package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.ProjectInputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;
import com.nfq.app_gestion_personal.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    Project toEntity(ProjectInputDto inputDto);

    ProjectOutputDto toOutput(Project entity);
}
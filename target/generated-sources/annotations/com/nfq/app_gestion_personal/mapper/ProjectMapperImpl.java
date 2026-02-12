package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.ProjectInputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;
import com.nfq.app_gestion_personal.entity.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T13:40:57+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.30 (Microsoft)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toEntity(ProjectInputDto inputDto) {
        if ( inputDto == null ) {
            return null;
        }

        Project project = new Project();

        project.setName( inputDto.getName() );
        project.setDescription( inputDto.getDescription() );
        project.setStartDate( inputDto.getStartDate() );
        project.setDepartment( inputDto.getDepartment() );

        return project;
    }

    @Override
    public ProjectOutputDto toOutput(Project entity) {
        if ( entity == null ) {
            return null;
        }

        ProjectOutputDto projectOutputDto = new ProjectOutputDto();

        projectOutputDto.setId( entity.getId() );
        projectOutputDto.setName( entity.getName() );
        projectOutputDto.setDescription( entity.getDescription() );
        projectOutputDto.setStartDate( entity.getStartDate() );
        projectOutputDto.setDepartment( entity.getDepartment() );

        return projectOutputDto;
    }
}

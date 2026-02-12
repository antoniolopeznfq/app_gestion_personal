package com.nfq.app_gestion_personal.service.impl;

import com.nfq.app_gestion_personal.dto.ProjectInputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;
import com.nfq.app_gestion_personal.entity.Project;
import com.nfq.app_gestion_personal.exception.ResourceAlreadyExistsException;
import com.nfq.app_gestion_personal.exception.ResourceNotFoundException;
import com.nfq.app_gestion_personal.mapper.ProjectMapper;
import com.nfq.app_gestion_personal.repository.ProjectRepository;
import com.nfq.app_gestion_personal.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public ProjectOutputDto createProject(ProjectInputDto inputDto) {
        if (projectRepository.existsByName(inputDto.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un proyecto con el nombre: " + inputDto.getName());
        }

        Project project = projectMapper.toEntity(inputDto);
        Project savedProject = projectRepository.save(project);

        return projectMapper.toOutput(savedProject);
    }

    @Override
    public List<ProjectOutputDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectOutputDto getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));

        return projectMapper.toOutput(project);
    }

    @Override
    public void deleteProject(String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Proyecto no encontrado con ID: " + id);
        }
        projectRepository.deleteById(id);
    }
}
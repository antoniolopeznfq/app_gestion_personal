package com.nfq.app_gestion_personal.service;

import com.nfq.app_gestion_personal.dto.ProjectInputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;

import java.util.List;

public interface ProjectService {
    ProjectOutputDto createProject(ProjectInputDto inputDto);
    List<ProjectOutputDto> getAllProjects();
    ProjectOutputDto getProjectById(String id);
    void deleteProject(String id);
}
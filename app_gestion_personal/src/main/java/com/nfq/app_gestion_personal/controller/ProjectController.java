package com.nfq.app_gestion_personal.controller;

import com.nfq.app_gestion_personal.dto.ProjectInputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;
import com.nfq.app_gestion_personal.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Proyectos", description = "Gestión del catálogo de proyectos de la empresa")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Crear proyecto", description = "Da de alta un nuevo proyecto en el sistema")
    @PostMapping
    public ResponseEntity<ProjectOutputDto> createProject(@RequestBody ProjectInputDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(input));
    }

    @Operation(summary = "Listar proyectos", description = "Obtiene todos los proyectos disponibles")
    @GetMapping
    public ResponseEntity<List<ProjectOutputDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @Operation(summary = "Obtener proyecto por ID", description = "Busca un proyecto específico")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectOutputDto> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Operation(summary = "Eliminar proyecto", description = "Borra un proyecto del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar proyecto", description = "Actualiza los datos de un proyecto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectOutputDto> updateProject(@PathVariable String id, @RequestBody ProjectInputDto input) {
        return ResponseEntity.ok(projectService.updateProject(id, input));
    }
}
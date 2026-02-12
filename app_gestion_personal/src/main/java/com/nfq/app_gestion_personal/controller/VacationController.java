package com.nfq.app_gestion_personal.controller;

import com.nfq.app_gestion_personal.dto.VacationInputDto;
import com.nfq.app_gestion_personal.dto.VacationOutputDto;
import com.nfq.app_gestion_personal.service.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacations")
@Tag(name = "Vacaciones", description = "Gestión de vacaciones de los empleados")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @Operation(summary = "Solicitar vacaciones", description = "Asigna un periodo de vacaciones a un empleado existente")
    @PostMapping
    public ResponseEntity<VacationOutputDto> createVacation(@RequestBody VacationInputDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacationService.createVacation(input));
    }

    @Operation(summary = "Listar todas las vacaciones", description = "Devuelve el historial completo de vacaciones")
    @GetMapping
    public ResponseEntity<List<VacationOutputDto>> getAllVacations() {
        return ResponseEntity.ok(vacationService.getAllVacations());
    }

    @Operation(summary = "Ver vacaciones de un empleado", description = "Lista vacaciones por ID de empleado")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<VacationOutputDto>> getVacationsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(vacationService.getVacationsByEmployeeId(employeeId));
    }

    @Operation(summary = "Ver vacaciones por Proyecto", description = "Lista vacaciones de todos los empleados asignados a un proyecto")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<VacationOutputDto>> getVacationsByProject(@PathVariable String projectId) {
        return ResponseEntity.ok(vacationService.getVacationsByProjectId(projectId));
    }

    @Operation(summary = "Modificar vacaciones", description = "Actualiza fechas o comentarios")
    @PutMapping("/{id}")
    public ResponseEntity<VacationOutputDto> updateVacation(@PathVariable String id, @RequestBody VacationInputDto input) {
        return ResponseEntity.ok(vacationService.updateVacation(id, input));
    }

    @Operation(summary = "Cancelar vacaciones", description = "Elimina el registro de vacaciones")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacation(@PathVariable String id) {
        vacationService.deleteVacation(id);
        return ResponseEntity.noContent().build();
    }
}
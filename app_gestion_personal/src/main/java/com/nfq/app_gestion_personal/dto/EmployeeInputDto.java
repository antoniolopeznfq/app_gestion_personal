package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO de Entrada.
 * Representa los datos que el cliente nos envía para crear un empleado.
 */
@Data // LOMBOK: Genera automáticamente Getters, Setters, toString, equals y hashCode.
public class EmployeeInputDto {

    @Schema(description = "Nombre del empleado", example = "Juan") // SWAGGER: Documenta el campo en la web
    private String name;

    @Schema(description = "Apellidos del empleado", example = "Pérez García")
    private String lastName;

    @Schema(description = "Correo corporativo", example = "juan.perez@empresa.com")
    private String email;

    @Schema(description = "Puesto de trabajo", example = "Desarrollador Junior")
    private String position;

    @Schema(description = "Salario bruto anual", example = "24000.00")
    private Double salary;

    @Schema(description = "Lista de IDs de los proyectos asignados", example = "[\"65df...\", \"65de...\"]")
    private List<String> projectIds;
}
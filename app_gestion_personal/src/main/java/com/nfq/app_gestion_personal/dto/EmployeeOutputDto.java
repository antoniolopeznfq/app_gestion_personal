package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO de Salida.
 * Representa los datos que enviamos de vuelta al cliente.
 * Se incluye el ID para que el frontend pueda identificar el registro.
 */
@Data // LOMBOK: Ahorra escribir código repetitivo
public class EmployeeOutputDto {

    @Schema(description = "Identificador único (generado por Mongo)", example = "65df...a1b2")
    private String id;

    @Schema(description = "Nombre del empleado")
    private String name;

    @Schema(description = "Apellidos del empleado")
    private String lastName;

    @Schema(description = "Correo corporativo")
    private String email;

    @Schema(description = "Puesto de trabajo")
    private String position;

    @Schema(description = "Salario bruto anual")
    private Double salary;

    @Schema(description = "Detalles de los proyectos asignados")
    private List<ProjectOutputDto> projects;
}
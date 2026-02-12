package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectOutputDto {

    @Schema(description = "Identificador único del proyecto", example = "65df1234a1b2c3d4e5f67890")
    private String id;

    @Schema(description = "Nombre del proyecto", example = "Migración Cloud")
    private String name;

    @Schema(description = "Descripción detallada del alcance", example = "Migración de servidores on-premise a AWS y reestructuración de red")
    private String description;

    @Schema(description = "Fecha de inicio del proyecto", example = "2023-09-01")
    private LocalDate startDate;

    @Schema(description = "Departamento responsable", example = "Infraestructura")
    private String department;
}
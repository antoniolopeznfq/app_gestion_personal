package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectInputDto {
    @Schema(example = "Migración Cloud")
    private String name;
    @Schema(example = "Migración de servidores on-premise a AWS")
    private String description;
    private LocalDate startDate;
    @Schema(example = "Infraestructura")
    private String department;
}
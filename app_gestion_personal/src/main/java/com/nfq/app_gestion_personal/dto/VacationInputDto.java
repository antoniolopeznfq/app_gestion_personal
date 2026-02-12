package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationInputDto {

    @Schema(description = "ID del empleado que solicita vacaciones", example = "65df...a1b2")
    private String employeeId;

    @Schema(description = "Fecha de inicio (YYYY-MM-DD)", example = "2023-08-01")
    private LocalDate startDate;

    @Schema(description = "Fecha de fin (YYYY-MM-DD)", example = "2023-08-15")
    private LocalDate endDate;

    @Schema(description = "Comentarios opcionales", example = "Vacaciones de verano")
    private String comments;
}
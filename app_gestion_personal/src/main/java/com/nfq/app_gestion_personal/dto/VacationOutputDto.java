package com.nfq.app_gestion_personal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationOutputDto {

    @Schema(description = "Identificador único de la solicitud de vacaciones", example = "65df1234a1b2c3d4e5f67890")
    private String id;

    @Schema(description = "Identificador del empleado asociado", example = "65df9876a1b2c3d4e5f65432")
    private String employeeId;

    @Schema(description = "Fecha de inicio de las vacaciones", example = "2023-08-01")
    private LocalDate startDate;

    @Schema(description = "Fecha de fin de las vacaciones", example = "2023-08-15")
    private LocalDate endDate;

    @Schema(description = "Comentarios o notas sobre el periodo", example = "Vacaciones de verano solicitadas con antelación")
    private String comments;
}
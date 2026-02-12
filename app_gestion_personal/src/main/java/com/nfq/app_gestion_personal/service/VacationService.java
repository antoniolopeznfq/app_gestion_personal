package com.nfq.app_gestion_personal.service;

import com.nfq.app_gestion_personal.dto.VacationInputDto;
import com.nfq.app_gestion_personal.dto.VacationOutputDto;
import java.util.List;

public interface VacationService {
    VacationOutputDto createVacation(VacationInputDto inputDto);

    // Métodos de lectura
    List<VacationOutputDto> getAllVacations();
    List<VacationOutputDto> getVacationsByEmployeeId(String employeeId);

    // El filtro complejo: Vacaciones de un proyecto
    List<VacationOutputDto> getVacationsByProjectId(String projectId);

    // Métodos de modificación
    VacationOutputDto updateVacation(String id, VacationInputDto inputDto);
    void deleteVacation(String id);
}

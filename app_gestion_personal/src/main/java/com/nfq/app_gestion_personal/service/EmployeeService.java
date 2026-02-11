package com.nfq.app_gestion_personal.service;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;

import java.util.List;

/**
 * Interfaz de la Lógica de Negocio.
 * Usa DTOs en los argumentos y retornos para no exponer la Entidad
 */
public interface EmployeeService {

    EmployeeOutputDto createEmployee(EmployeeInputDto inputDto);

    List<EmployeeOutputDto> getAllEmployees();

    void deleteEmployee(String id);

    EmployeeOutputDto getEmployeeById(String id);
}
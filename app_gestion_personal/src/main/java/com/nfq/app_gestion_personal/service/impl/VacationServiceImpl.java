package com.nfq.app_gestion_personal.service.impl;

import com.nfq.app_gestion_personal.dto.VacationInputDto;
import com.nfq.app_gestion_personal.dto.VacationOutputDto;
import com.nfq.app_gestion_personal.entity.Employee;
import com.nfq.app_gestion_personal.entity.Vacation;
import com.nfq.app_gestion_personal.exception.ResourceNotFoundException;
import com.nfq.app_gestion_personal.mapper.VacationMapper;
import com.nfq.app_gestion_personal.repository.EmployeeRepository;
import com.nfq.app_gestion_personal.repository.VacationRepository;
import com.nfq.app_gestion_personal.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationServiceImpl implements VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationMapper vacationMapper;

    @Override
    public VacationOutputDto createVacation(VacationInputDto inputDto) {
        if (!employeeRepository.existsById(inputDto.getEmployeeId())) {
            throw new ResourceNotFoundException("No se puede asignar vacaciones. Empleado no encontrado: " + inputDto.getEmployeeId());
        }
        Vacation vacation = vacationMapper.toEntity(inputDto);
        return vacationMapper.toOutput(vacationRepository.save(vacation));
    }

    @Override
    public List<VacationOutputDto> getAllVacations() {
        return vacationRepository.findAll().stream()
                .map(vacationMapper::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacationOutputDto> getVacationsByEmployeeId(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Empleado no encontrado: " + employeeId);
        }
        return vacationRepository.findByEmployeeId(employeeId).stream()
                .map(vacationMapper::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacationOutputDto> getVacationsByProjectId(String projectId) {
        // 1. Buscamos empleados que trabajen en este proyecto (usando la query que ya tienes en EmployeeRepository)
        List<Employee> employeesInProject = employeeRepository.findByProjectIds(projectId);

        // 2. Extraemos solo los IDs de esos empleados
        List<String> employeeIds = employeesInProject.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        // 3. Buscamos las vacaciones de esos IDs
        List<Vacation> vacations = vacationRepository.findByEmployeeIdIn(employeeIds);

        return vacations.stream()
                .map(vacationMapper::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public VacationOutputDto updateVacation(String id, VacationInputDto inputDto) {
        Vacation existingVacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacación no encontrada con ID: " + id));

        // Validamos si cambian el empleado
        if (!existingVacation.getEmployeeId().equals(inputDto.getEmployeeId())) {
            if (!employeeRepository.existsById(inputDto.getEmployeeId())) {
                throw new ResourceNotFoundException("El nuevo empleado asignado no existe.");
            }
        }

        // Actualizamos campos manualmente o usando el mapper si tuviéramos método de update
        existingVacation.setEmployeeId(inputDto.getEmployeeId());
        existingVacation.setStartDate(inputDto.getStartDate());
        existingVacation.setEndDate(inputDto.getEndDate());
        existingVacation.setComments(inputDto.getComments());

        return vacationMapper.toOutput(vacationRepository.save(existingVacation));
    }

    @Override
    public void deleteVacation(String id) {
        if (!vacationRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Vacación no encontrada con ID: " + id);
        }
        vacationRepository.deleteById(id);
    }
}
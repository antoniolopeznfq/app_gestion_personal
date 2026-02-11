package com.nfq.app_gestion_personal.service.impl;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;
import com.nfq.app_gestion_personal.entity.Employee;
import com.nfq.app_gestion_personal.exception.ResourceAlreadyExistsException;
import com.nfq.app_gestion_personal.exception.ResourceNotFoundException;
import com.nfq.app_gestion_personal.mapper.EmployeeMapper;
import com.nfq.app_gestion_personal.repository.EmployeeRepository;
import com.nfq.app_gestion_personal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la Lógica de Negocio.
 */
@Service // Indica que esta clase contiene lógica de negocio y debe ser gestionada por Spring.
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeOutputDto createEmployee(EmployeeInputDto inputDto) {
        // Comprobar si el email ya existe
        if (employeeRepository.findByEmail(inputDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe un empleado con el email: " + inputDto.getEmail());
        }

        // 1. Convertimos el DTO (JSON) a Entidad
        Employee employee = employeeMapper.toEntity(inputDto);

        // 2. Guardamos en base de datos
        Employee savedEmployee = employeeRepository.save(employee);

        // 3. Convertimos a DTO de salida
        return employeeMapper.toOutput(savedEmployee);
    }

    @Override
    public List<EmployeeOutputDto> getAllEmployees() {
        // 1. Recuperamos todos de la base de datos
        List<Employee> employees = employeeRepository.findAll();

        // 2. Usamos Java Streams para convertir la lista de Entidades a lista de DTOs
        return employees.stream()
                .map(employeeMapper::toOutput) // Referencia al método del mapper
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeOutputDto getEmployeeById(String id) {
        // Buscamos por ID. Si no existe, lanzamos la excepción personalizada.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

        // Convertimos a DTO de salida
        return employeeMapper.toOutput(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        // Antes de borrar, comprobamos si existe. Si no, lanzamos error 404.
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Empleado no encontrado con ID: " + id);
        }
        // Delegamos el borrado en el repositorio
        employeeRepository.deleteById(id);
    }
}
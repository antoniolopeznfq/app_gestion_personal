package com.nfq.app_gestion_personal.service.impl;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;
import com.nfq.app_gestion_personal.dto.ProjectOutputDto;
import com.nfq.app_gestion_personal.entity.Employee;
import com.nfq.app_gestion_personal.entity.Project;
import com.nfq.app_gestion_personal.exception.ResourceAlreadyExistsException;
import com.nfq.app_gestion_personal.exception.ResourceNotFoundException;
import com.nfq.app_gestion_personal.mapper.EmployeeMapper;
import com.nfq.app_gestion_personal.mapper.ProjectMapper;
import com.nfq.app_gestion_personal.repository.EmployeeRepository;
import com.nfq.app_gestion_personal.repository.ProjectRepository;
import com.nfq.app_gestion_personal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public EmployeeOutputDto createEmployee(EmployeeInputDto inputDto) {
        // Comprobar si el email ya existe
        if (employeeRepository.findByEmail(inputDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe un empleado con el email: " + inputDto.getEmail());
        }

        // Validar que los proyectos asignados existan en la base de datos
        validateProjectsExist(inputDto.getProjectIds());

        // 1. Convertimos el DTO (JSON) a Entidad
        Employee employee = employeeMapper.toEntity(inputDto);

        // 2. Guardamos en base de datos
        Employee savedEmployee = employeeRepository.save(employee);

        // 3. Convertimos a DTO de salida enriquecido con los datos de los proyectos
        return mapToOutputWithProjects(savedEmployee);
    }

    @Override
    public List<EmployeeOutputDto> getAllEmployees() {
        // 1. Recuperamos todos de la base de datos
        List<Employee> employees = employeeRepository.findAll();

        // 2. Usamos Java Streams para convertir la lista de Entidades a lista de DTOs con proyectos completos
        return employees.stream()
                .map(this::mapToOutputWithProjects)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeOutputDto getEmployeeById(String id) {
        // Buscamos por ID. Si no existe, lanzamos la excepción personalizada.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

        // Convertimos a DTO de salida enriquecido
        return mapToOutputWithProjects(employee);
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

    @Override
    public EmployeeOutputDto updateEmployee(String id, EmployeeInputDto inputDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

        // Validar que los nuevos proyectos existan
        validateProjectsExist(inputDto.getProjectIds());

        existingEmployee.setName(inputDto.getName());
        existingEmployee.setLastName(inputDto.getLastName());
        existingEmployee.setEmail(inputDto.getEmail());
        existingEmployee.setPosition(inputDto.getPosition());
        existingEmployee.setSalary(inputDto.getSalary());

        // Actualizamos la lista de IDs de proyectos
        existingEmployee.setProjectIds(inputDto.getProjectIds());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return mapToOutputWithProjects(updatedEmployee);
    }

    /**
     * Valida que todos los IDs de proyectos proporcionados existan en la base de datos.
     */
    private void validateProjectsExist(List<String> projectIds) {
        if (projectIds != null && !projectIds.isEmpty()) {
            for (String projectId : projectIds) {
                if (!projectRepository.existsById(projectId)) {
                    throw new ResourceNotFoundException("Proyecto no encontrado con ID: " + projectId);
                }
            }
        }
    }

    /**
     * Convierte un Employee a EmployeeOutputDto rellenando la lista completa de objetos Project.
     */
    private EmployeeOutputDto mapToOutputWithProjects(Employee employee) {
        // 1. Convertimos los datos básicos del empleado
        EmployeeOutputDto output = employeeMapper.toOutput(employee);

        if (employee.getProjectIds() != null && !employee.getProjectIds().isEmpty()) {
            Iterable<Project> projectsIterable = projectRepository.findAllById(employee.getProjectIds());
            List<Project> projects = new ArrayList<>();
            projectsIterable.forEach(projects::add);

           List<ProjectOutputDto> projectDtos = projects.stream()
                    .map(project -> projectMapper.toOutput(project))
                    .collect(Collectors.toList());

            // Ahora sí, guardamos la lista de DTOs
            output.setProjects(projectDtos);
        } else {
            // Si no tiene proyectos, devolvemos lista vacía para que no sea null
            output.setProjects(new ArrayList<>());
        }
        return output;
    }
}
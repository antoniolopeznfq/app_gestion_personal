package com.nfq.app_gestion_personal.controller;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;
import com.nfq.app_gestion_personal.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que es un controlador REST (devuelve JSON, no vistas HTML)
@RequestMapping("/employees") // Todas las rutas de esta clase empezarán por /employees
@Tag(name = "Empleados", description = "Operaciones sobre empleados") // Título para Swagger
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Crear un empleado", description = "Crea un nuevo registro en base de datos")
    @PostMapping // Responde a peticiones POST
    public ResponseEntity<EmployeeOutputDto> createEmployee(@RequestBody EmployeeInputDto input) {
        EmployeeOutputDto newEmployee = employeeService.createEmployee(input);

        // Devolvemos código 201 (CREATED) y el objeto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @Operation(summary = "Listar empleados", description = "Devuelve todos los empleados registrados")
    @GetMapping // Responde a peticiones GET a /employees
    public ResponseEntity<List<EmployeeOutputDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Obtener empleado por ID", description = "Devuelve un único empleado buscando por su ID")
    @GetMapping("/{id}") // Responde a peticiones GET a /employees/{id}
    public ResponseEntity<EmployeeOutputDto> getEmployeeById(@PathVariable String id) {
        // Llamamos al servicio. Si no existe, el servicio lanza la excepción y el GlobalExceptionHandler responde 404.
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Eliminar empleado", description = "Borra un empleado por su ID")
    @DeleteMapping("/{id}") // Responde a DELETE /employees/{id}
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);

        // Devolvemos 204 (NO CONTENT) porque el borrado ha sido exitoso y no hay nada que devolver
        return ResponseEntity.noContent().build();
    }
}
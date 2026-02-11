package com.nfq.app_gestion_personal.repository;

import com.nfq.app_gestion_personal.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Capa de Persistencia (Repositorio).
 * Extiende de MongoRepository, lo que nos da acceso a métodos como:
 * save(), findAll(), findById(), deleteById(), etc.
 *
 * <Employee, String>: Indicamos que gestiona la entidad 'Employee' y su ID es tipo 'String'.
 */
@Repository // Indica a Spring que esto es un componente de acceso a datos (y gestiona sus errores).
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    // Spring Data es capaz de crear la query solo con el nombre del método.
    // Esto buscará en Mongo un documento donde el campo 'email' coincida con el parámetro.
    Optional<Employee> findByEmail(String email);

    // Buscar por nombre exacto
    List<Employee> findByName(String name);

    // Buscar por puesto de trabajo
    List<Employee> findByPosition(String position);

    // Buscar los que cobren más de X cantidad (Greater Than)
    List<Employee> findBySalaryGreaterThan(Double minSalary);

    // Buscar por puesto Y ordenar por salario descendente
    List<Employee> findByPositionOrderBySalaryDesc(String position);
}
package com.nfq.app_gestion_personal.repository;

import com.nfq.app_gestion_personal.entity.Vacation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends MongoRepository<Vacation, String> {

    // Spring Data genera la query automáticamente por el nombre del método
    List<Vacation> findByEmployeeId(String employeeId);

    // Buscar vacaciones donde el employeeId esté dentro de una lista dada
    List<Vacation> findByEmployeeIdIn(List<String> employeeIds);
}
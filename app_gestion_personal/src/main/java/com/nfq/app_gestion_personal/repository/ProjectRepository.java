package com.nfq.app_gestion_personal.repository;

import com.nfq.app_gestion_personal.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    // Método derivado para comprobar si existe un proyecto con ese nombre
    boolean existsByName(String name);
}
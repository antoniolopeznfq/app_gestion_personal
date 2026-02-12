package com.nfq.app_gestion_personal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data // Genera Getters, Setters, toString, equals, hashcode
@NoArgsConstructor // Genera constructor vacío
@AllArgsConstructor // Genera constructor con todos los argumentos
@Document(collection = "employees") // Nombre de la colección en MongoDB
public class Employee {

    @Id
    private String id;

    private String name;
    private String lastName;
    private String email;
    private String position;
    private Double salary;
    private List<String> projects;
}
package com.nfq.app_gestion_personal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vacations")
public class Vacation {

    @Id
    private String id;

    // Guardamos el ID del empleado para relacionarlo (como una Foreign Key)
    private String employeeId;

    private LocalDate startDate;
    private LocalDate endDate;
    private String comments;
}
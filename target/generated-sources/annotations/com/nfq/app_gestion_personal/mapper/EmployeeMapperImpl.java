package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.EmployeeInputDto;
import com.nfq.app_gestion_personal.dto.EmployeeOutputDto;
import com.nfq.app_gestion_personal.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T13:40:57+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.30 (Microsoft)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEntity(EmployeeInputDto inputDto) {
        if ( inputDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        List<String> list = inputDto.getProjectIds();
        if ( list != null ) {
            employee.setProjectIds( new ArrayList<String>( list ) );
        }
        employee.setName( inputDto.getName() );
        employee.setLastName( inputDto.getLastName() );
        employee.setEmail( inputDto.getEmail() );
        employee.setPosition( inputDto.getPosition() );
        employee.setSalary( inputDto.getSalary() );

        return employee;
    }

    @Override
    public EmployeeOutputDto toOutput(Employee entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeOutputDto employeeOutputDto = new EmployeeOutputDto();

        employeeOutputDto.setId( entity.getId() );
        employeeOutputDto.setName( entity.getName() );
        employeeOutputDto.setLastName( entity.getLastName() );
        employeeOutputDto.setEmail( entity.getEmail() );
        employeeOutputDto.setPosition( entity.getPosition() );
        employeeOutputDto.setSalary( entity.getSalary() );

        return employeeOutputDto;
    }
}

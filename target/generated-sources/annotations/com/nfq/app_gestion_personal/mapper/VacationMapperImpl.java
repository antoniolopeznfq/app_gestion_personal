package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.VacationInputDto;
import com.nfq.app_gestion_personal.dto.VacationOutputDto;
import com.nfq.app_gestion_personal.entity.Vacation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T13:40:57+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.30 (Microsoft)"
)
@Component
public class VacationMapperImpl implements VacationMapper {

    @Override
    public Vacation toEntity(VacationInputDto inputDto) {
        if ( inputDto == null ) {
            return null;
        }

        Vacation vacation = new Vacation();

        vacation.setEmployeeId( inputDto.getEmployeeId() );
        vacation.setStartDate( inputDto.getStartDate() );
        vacation.setEndDate( inputDto.getEndDate() );
        vacation.setComments( inputDto.getComments() );

        return vacation;
    }

    @Override
    public VacationOutputDto toOutput(Vacation entity) {
        if ( entity == null ) {
            return null;
        }

        VacationOutputDto vacationOutputDto = new VacationOutputDto();

        vacationOutputDto.setId( entity.getId() );
        vacationOutputDto.setEmployeeId( entity.getEmployeeId() );
        vacationOutputDto.setStartDate( entity.getStartDate() );
        vacationOutputDto.setEndDate( entity.getEndDate() );
        vacationOutputDto.setComments( entity.getComments() );

        return vacationOutputDto;
    }
}

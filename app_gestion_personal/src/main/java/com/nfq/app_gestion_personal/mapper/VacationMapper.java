package com.nfq.app_gestion_personal.mapper;

import com.nfq.app_gestion_personal.dto.VacationInputDto;
import com.nfq.app_gestion_personal.dto.VacationOutputDto;
import com.nfq.app_gestion_personal.entity.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacationMapper {

    @Mapping(target = "id", ignore = true)
    Vacation toEntity(VacationInputDto inputDto);

    VacationOutputDto toOutput(Vacation entity);
}
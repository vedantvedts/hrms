package com.vts.hrms.mapper;

import com.vts.hrms.dto.MandatoryTrainingDTO;
import com.vts.hrms.entity.MandatoryTraining;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MandatoryTrainingMapper extends EntityMapper<MandatoryTrainingDTO, MandatoryTraining> {
}

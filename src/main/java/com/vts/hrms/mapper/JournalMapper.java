package com.vts.hrms.mapper;

import com.vts.hrms.dto.JournalDTO;
import com.vts.hrms.entity.Journal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JournalMapper extends EntityMapper<JournalDTO, Journal> {
}

package com.vts.hrms.service;

import com.vts.hrms.dto.*;
import com.vts.hrms.entity.*;
import com.vts.hrms.exception.NotFoundException;
import com.vts.hrms.mapper.SponsorshipMapper;
import com.vts.hrms.repository.SponsorshipRepository;
import com.vts.hrms.util.CommonUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SponsorshipService {

    private static final Logger log = LoggerFactory.getLogger(SponsorshipService.class);

    @Value("${x_api_key}")
    private String xApiKey;

    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;
    private final MasterCacheService masterCacheService;

    public SponsorshipService(SponsorshipRepository sponsorshipRepository, SponsorshipMapper sponsorshipMapper, MasterCacheService masterCacheService) {
        this.sponsorshipRepository = sponsorshipRepository;
        this.sponsorshipMapper = sponsorshipMapper;
        this.masterCacheService = masterCacheService;
    }

    @Cacheable(value = "sponsorshipCache", key = "#type")
    public List<SponsorshipDTO> getAllSponsorshipList(String type, String username) {
        log.info("Request to fetch sponsorship list for type {} by {}", type, username);

        List<Sponsorship> list = sponsorshipRepository.findAllByDegreeType(type);
        List<SponsorshipDTO> dtoList = sponsorshipMapper.toDto(list);

        Map<Long, EmployeeDTO> employeeDTOMap = masterCacheService.getLongEmployeeDTOMap();

        dtoList.forEach(data -> {
            EmployeeDTO employeeDTO = employeeDTOMap.get(data.getEmpId());
             if(employeeDTO!=null) {
                 data.setEmpNo(employeeDTO.getEmpNo());
                 data.setEmployeeName(CommonUtil.buildEmployeeName(employeeDTO, false));
                 data.setEmpDesigCode(employeeDTO.getEmpDesigName());
                 data.setDesigCadre(employeeDTO.getDesigCadre());
                 data.setEmpDivCode(employeeDTO.getEmpDivCode());
             }
        });

        return dtoList;
    }


    @CacheEvict(value = {"sponsorshipCache", "mtechReportCache", "phdReportCache"}, allEntries = true)
    @Transactional
    public SponsorshipDTO addSponsorshipData(@Valid SponsorshipDTO dto, String username) {
        log.info("Request to add sponsorship by {}", username);

        Sponsorship sponsorship = sponsorshipMapper.toEntity(dto);
        sponsorship.setCreatedBy(username);
        sponsorship.setCreatedDate(LocalDateTime.now());
        sponsorship.setIsActive(1);

        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDto(sponsorship);
    }


    @CacheEvict(value = {"sponsorshipCache", "mtechReportCache", "phdReportCache"}, allEntries = true)
    @Transactional
    public Optional<SponsorshipDTO> editSponsorshipData(SponsorshipDTO dto, String username) {
        log.info("Request to edit sponsorship for id {} by {}", dto.getSponsorshipId(), username);

        return sponsorshipRepository.findById(dto.getSponsorshipId())
                .map(existing -> {
                    existing.setModifiedBy(username);
                    existing.setModifiedDate(LocalDateTime.now());
                    sponsorshipMapper.partialUpdate(existing, dto);
                    return existing;
                })
                .map(sponsorshipRepository::save)
                .map(sponsorshipMapper::toDto);
    }


    @Transactional
    public SponsorshipDTO getSponsorshipById(Long sponsorshipId, String username) {
        log.info("Request to fetch Sponsorship data for id {} by {}", sponsorshipId, username);

        if (sponsorshipId == null) {
            throw new NotFoundException("Sponsorship id cannot be null");
        }

        Sponsorship sponsorship = sponsorshipRepository.findById(sponsorshipId)
                .orElseThrow(() -> new NotFoundException("Sponsorship data not found"));

        return sponsorshipMapper.toDto(sponsorship);
    }


}

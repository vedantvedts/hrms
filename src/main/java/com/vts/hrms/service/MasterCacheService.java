package com.vts.hrms.service;

import com.vts.hrms.dto.DivisionDTO;
import com.vts.hrms.dto.EmployeeDTO;
import com.vts.hrms.dto.ProjectEmployeeDto;
import com.vts.hrms.dto.ProjectMasterDTO;
import com.vts.hrms.entity.Course;
import com.vts.hrms.entity.CourseType;
import com.vts.hrms.entity.Organizer;
import com.vts.hrms.entity.Status;
import com.vts.hrms.repository.CourseRepository;
import com.vts.hrms.repository.CourseTypeRepository;
import com.vts.hrms.repository.OrganizerRepository;
import com.vts.hrms.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MasterCacheService {

    @Value("${x_api_key}")
    private String xApiKey;

    @Value("${labCode}")
    private String labCode;

    private final MasterClientService masterClient;
    private final OrganizerRepository organizerRepository;
    private final CourseRepository courseRepository;
    private final StatusRepository statusRepository;
    private final CourseTypeRepository courseTypeRepository;

    public MasterCacheService(MasterClientService masterClient, OrganizerRepository organizerRepository, CourseRepository courseRepository, StatusRepository statusRepository, CourseTypeRepository courseTypeRepository) {
        this.masterClient = masterClient;
        this.organizerRepository = organizerRepository;
        this.courseRepository = courseRepository;
        this.statusRepository = statusRepository;
        this.courseTypeRepository = courseTypeRepository;
    }

    @Cacheable(value = "employeeMapCache", key = "'employeeMap'")
    public Map<Long, EmployeeDTO> getLongEmployeeDTOMap() {
        List<EmployeeDTO> employeeList = masterClient.getEmployeeMasterList(xApiKey);

        return employeeList.stream()
                .filter(e -> labCode != null && labCode.equalsIgnoreCase(e.getLabCode()))
                .collect(Collectors.toMap(EmployeeDTO::getEmpId, emp -> emp));
    }

    @Cacheable(value = "divisionMapCache", key = "'divisionMap'")
    public Map<Long, DivisionDTO> getDivisionDTOMap() {
        List<DivisionDTO> divisionDTOList = masterClient.getDivisionMaster(xApiKey);

        return divisionDTOList.stream()
                .collect(Collectors.toMap(DivisionDTO::getDivisionId, emp -> emp));
    }

    @Cacheable(value = "projectMapCache", key = "'projectMap'")
    public Map<Long, ProjectMasterDTO> getProjectDTOMap() {
        List<ProjectMasterDTO> projectMasterDTOList = masterClient.getProjectMasterList(xApiKey);

        return projectMasterDTOList.stream()
                .collect(Collectors.toMap(ProjectMasterDTO::getProjectId, emp -> emp));
    }

    @Cacheable(value = "organizerCache", key = "'organizers'")
    public Map<Long, Organizer> getOrganizerMap() {
        return organizerRepository.findAllByIsActive(1).stream()
                .collect(Collectors.toMap(Organizer::getOrganizerId, Function.identity()));
    }

    @Cacheable(value = "courseCache", key = "'courses'")
    public Map<Long, Course> getCourseMap() {
        return courseRepository.findAllByIsActive(1).stream()
                .collect(Collectors.toMap(Course::getCourseId, Function.identity()));
    }

    @Cacheable(value = "statusCache", key = "'status'")
    public Map<String, Status> getStatusMap() {
        return statusRepository.findAll().stream()
                .collect(Collectors.toMap(Status::getStatusCode, Function.identity()));
    }

    @Cacheable(value = "courseTypeCache", key = "'courseType'")
    public Map<Long, CourseType> getCourseTypeMap() {
        return courseTypeRepository.findAll().stream()
                .collect(Collectors.toMap(CourseType::getCourseTypeId, Function.identity()));
    }

    @Cacheable(value = "projectEmployeeCache", key = "'projectEmployee'")
    public Map<Long, List<ProjectEmployeeDto>> getProjectEmployeeMap() {

        List<ProjectEmployeeDto> list =
                masterClient.getProjectListByEmpId(xApiKey, null);

        return list.stream()
                .filter(p -> p.getEmpId() != null)
                .collect(Collectors.groupingBy(ProjectEmployeeDto::getEmpId));
    }

}

package com.vts.hrms.service;

import com.vts.hrms.dto.CourseDashboardDTO;
import com.vts.hrms.dto.RequisitionDashboardDTO;
import com.vts.hrms.repository.CourseRepository;
import com.vts.hrms.repository.RequisitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final CourseRepository courseRepository;
    private final RequisitionRepository requisitionRepository;

    public DashboardService(CourseRepository courseRepository, RequisitionRepository requisitionRepository) {
        this.courseRepository = courseRepository;
        this.requisitionRepository = requisitionRepository;
    }

    public List<CourseDashboardDTO> getOrganizerCourseDashboard(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching organizer wise course dashboard from {} to {}", startDate, endDate);

        return courseRepository.getOrganizerWiseCourseCount(startDate, endDate);
    }


    public List<RequisitionDashboardDTO> getOrganizerRequisitionDashboard() {
        log.info("Fetching organizer wise requisition dashboard");

        return requisitionRepository.getOrganizerWiseRequisitionStats();
    }

    public List<RequisitionDashboardDTO> getRequisitionFilterDashboard(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching organizer wise requisition dashboard from {} to {}", startDate, endDate);

        return requisitionRepository.getRequisitionFilterDashboard(startDate, endDate);
    }

    public List<RequisitionDashboardDTO> getUserRequisitionFilter(Long empId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching requisition dashboard for empId {} from {} to {}", empId, startDate, endDate);

        return requisitionRepository.getRequisitionFilterUserDashboard(empId, startDate, endDate);
    }
}

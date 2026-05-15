package com.vts.hrms.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.vts.hrms.auth.AuthenticationController;
import com.vts.hrms.dto.*;
import com.vts.hrms.entity.AuditStamping;
import com.vts.hrms.entity.Login;
import com.vts.hrms.entity.RoleSecurity;
import com.vts.hrms.repository.LoginRepository;
import com.vts.hrms.service.AdminService;
import com.vts.hrms.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    private final LoginRepository loginRepository;
    private DateTimeFormatter formatter;

    public AdminController(AdminService adminService,LoginRepository loginRepository) {

        this.adminService = adminService;
        this.loginRepository=loginRepository;
    }


    @GetMapping(value = "/roles")
    public ResponseEntity<List<RoleDTO>> getRoleList() {
        List<RoleDTO> list = adminService.getRoleList();

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/user-list")
    public ResponseEntity<ApiResponse> getAllUsersList() {
        List<UserResponseDTO> list = adminService.getUserList();

        return ResponseEntity.ok(
                new ApiResponse(true, "User list fetched", list)
        );
    }

    @GetMapping(value = "/user/{loginId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long loginId) {
        UserResponseDTO dto = adminService.getUserById(loginId);

        return ResponseEntity.ok(
                new ApiResponse(true, "user data fetched", dto)
        );
    }

    @GetMapping("/exists/{username}")
    public boolean existsUsername(@PathVariable String username) {
        return adminService.checkUsernameExists(username);
    }

    @PostMapping(value = "/add-user")
    public ResponseEntity<UserResponseDTO> addNewUser(@RequestBody UserResponseDTO dto, @RequestHeader String username) {
        UserResponseDTO saved = adminService.addNewUser(dto, username);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping(value = "/update-user/{loginId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserResponseDTO dto, @RequestHeader String username) {
        UserResponseDTO saved = adminService.updateUser(dto, username);
        return ResponseEntity.ok(
                new ApiResponse(true, "User updated successfully", saved)
        );
    }

    @PostMapping(value = "/role-update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> roleUpdate(@RequestHeader String username, @RequestBody UserResponseDTO dto) throws Exception{
        LOG.info( "Inside roleUpdate : username :{} , dto : {} ", username, dto);
        try {
            Login login = loginRepository.findByUsernameAndIsActive(dto.getUsername(),1);
            if(login!=null) {
                dto.setLoginId(login.getLoginId());
                adminService.updateUser(dto, username);
            }else {
               adminService.addNewUser(dto, username);
            }
            return new ResponseEntity<String>("200" , HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching userInsert:{} ",e.getMessage());
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }


    @RequestMapping(value = "/header-module", method = RequestMethod.POST, produces = "application/json")
    public List<FormModuleDto> headerModule(@RequestBody Long FormRoleId) throws Exception {

        return adminService.formModuleList(FormRoleId);
    }

    @RequestMapping(value = "/header-detail", method = RequestMethod.POST, produces = "application/json")
    public List<FormDetailDto> headerDetail(@RequestBody Long FormRoleId) throws Exception {

        return adminService.formModuleDetailList(FormRoleId);
    }

    @PostMapping(value = "/form-modules-list", produces = "application/json")
    public ResponseEntity<List<FormModuleDto>> formModule() throws Exception {

        List<FormModuleDto> list = null;

        try {
            list = adminService.getformModulelist();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "form-role-access-list", produces = "application/json")
    public ResponseEntity<List<FormRoleAccessDto>> formRoleAccessList(@RequestBody Map<String, String> request) throws Exception {

        List<FormRoleAccessDto> list = null;

        try {
            String roleId = request.get("roleId");
            String formModuleId = request.get("formModuleId");
            list = adminService.getformRoleAccessList(roleId, formModuleId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "update-form-role-access", produces = "application/json")
    public String updateFormRoleAccess(@RequestBody FormRoleAccessDto accessDto, @RequestHeader String username) throws Exception {

        String result = null;
        try {
            result = adminService.updateformroleaccess(accessDto, username);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @GetMapping(value = "/user-login-access")
    public ResponseEntity<Boolean> checkAccess(@RequestHeader String username) {
        LOG.info(" REST request to check access: {}", username);
        try {
            boolean hasAccess = adminService.hasAccess(username);
            return ResponseEntity.ok(hasAccess);
        } catch (Exception e) {
            LOG.error("Error while fetching user access {}", e.getMessage(), e);
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping(value = "/notification-count", produces = "application/json")
    public ResponseEntity<Integer> getNotificationCount(@RequestHeader String username) throws Exception {
        LOG.info(" Inside get get-notification-count{}", username);
        Integer result = adminService.getNotificationCount(username);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/notification-list", produces = "application/json")
    public ResponseEntity<List<NotificationDTO>> getNotification(@RequestHeader String username) throws Exception {
        LOG.info(" Inside get get-notification List {}", username);
        List<NotificationDTO> result = adminService.getNotificationList(username);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/update-notification", produces = "application/json")
    public ResponseEntity<Long> updateNotification(@RequestHeader String username, @RequestParam String notificationId) throws Exception {
        LOG.info(" Inside  update-notification  {}", username);
        long result = adminService.updateNotification(username, notificationId);
        if (result != 0) {
            return new ResponseEntity<>(200L, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/get-role-username", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> getRoleByUsername(@RequestHeader String username) {
        try {
            Login login = loginRepository.findByUsernameAndIsActive(username,1);

            List<Long> roleIds = login.getRoleSecurity().stream()
                    .map(RoleSecurity::getRoleId)
                    .collect(Collectors.toList());

            // You can now store these or perform logic
            LOG.info("Extracted Role IDs for {}: {}", username, roleIds);

            return roleIds; // Returns a JSON array like [1, 2, 3]

        } catch (Exception e) {
            LOG.error("Error processing roles: ", e);
            return new ArrayList<>();
        }
    }

    @GetMapping(value = "/audit-stamping-list")
    public ResponseEntity<List<AuditStampingDTO>> auditStampingList(@RequestHeader String username,
                                                                    @RequestParam String selUser,
                                                                    @RequestParam LocalDate fromDate,
                                                                    @RequestParam LocalDate toDate) throws Exception
    {
        try {

            LOG.info("Inside auditStampingList | User: {},fromDate: {},toDate: {}", username,fromDate,toDate);

            List<AuditStampingDTO> dto = adminService.auditStampingList(selUser, fromDate, toDate);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error in auditStampingList: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="custom-audit-stamping-login" ,produces="application/json")
    public String logIn(@RequestBody String username, Authentication authentication, HttpServletRequest request)throws Exception {
        LOG.info(" Inside custom-audit-stamping-login: user:{}, ",username);
        long result=0;
        username = username.replace("\"", "");
        String IpAddress;
        try{
            IpAddress = request.getRemoteAddr();
            if("0:0:0:0:0:0:0:1".equalsIgnoreCase(IpAddress))
            {
                InetAddress ip = InetAddress.getLocalHost();
                IpAddress= ip.getHostAddress();
            }
        }
        catch(Exception e)
        {
            IpAddress="Not Available";
        }
        try{
            Login login = loginRepository.findByUsernameAndIsActive(username,1);
            AuditStamping stamping=new AuditStamping();
            stamping.setLoginId(login.getLoginId());
            stamping.setLoginDate(LocalDate.now());
            stamping.setLoginDatetime(LocalDateTime.now());
            stamping.setUsername(login.getUsername());
            stamping.setIpAddress(IpAddress);
            result = adminService.loginStampingInsert(stamping);
        }catch (Exception e) {
            LOG.error("Error in custom-audit-stamping-login: {}", e.getMessage(), e);
        }
        return String.valueOf(result);

    }


    @PostMapping(value = "custom-audit-stamping-logout", produces = "application/json")
    public String logout(@RequestBody JsonNode requestBody, Authentication authentication) throws Exception {
        LOG.info( " Inside custom-auditStamping-logout {}", authentication.getName());
        long result = 0;

        String username = requestBody.get("username").asText();
        String logoutType = requestBody.get("logoutType").asText();
        Login login = loginRepository.findByUsernameAndIsActive(username,1);
        Long loginId = login.getLoginId();

        try {
            if (loginId!=null) {
                AuditStamping stamping = new AuditStamping();
                stamping.setAuditStampingId(adminService.lastLoginStampingId(loginId));
                stamping.setLogoutType(logoutType);
                stamping.setLogoutDateTime(LocalDateTime.now());
                result = adminService.loginStampingUpdate(stamping);
            }
        } catch (Exception e) {
            LOG.error(" error in custom-audit-stamping-logout {}", e.getMessage(),e);
        }
        return String.valueOf(result);
    }

    @GetMapping("/get-login-stats")
    public DashboardLoginStatsDTO getLoginStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return adminService.getLoginStats(startDate, endDate);
    }

    @PutMapping(value = "/change-password", produces="application/json")
    public ResponseEntity<String> updatePassword(@RequestHeader String username,@RequestBody ChangePasswordDTO dto){
        LOG.info("REST request to change password for user :{}",username);
        try {
            Integer count = adminService.changePassword(dto);
            if(count > 0){
                return ResponseEntity.ok(count+"");
            }
            return ResponseEntity.badRequest().body("0");

        } catch (Exception e) {
            LOG.error("Error while changing the password for user: {} {}", username, e.getMessage(), e);
            return new ResponseEntity<>("0", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-license", produces = "application/json")
    public ResponseEntity<Boolean> getLicense() {
        LOG.info("Inside getLicense()");
        try {
            boolean isValid = adminService.getLicense();
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            LOG.error("Error in getLicense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }

}

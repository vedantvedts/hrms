package com.vts.hrms.util;


import com.vts.hrms.dto.EmployeeDTO;

import java.util.Optional;

public class CommonUtil {

    public static String buildEmployeeName(EmployeeDTO emp, boolean includeDesignation) {

        if (emp == null) return "";

        String title = Optional.ofNullable(emp.getTitle())
                .filter(t -> !t.isBlank())
                .orElse(null);

        String salutation = Optional.ofNullable(emp.getSalutation())
                .filter(s -> !s.isBlank())
                .orElse(null);

        String name = Optional.ofNullable(emp.getEmpName()).orElse("");
        String designation = Optional.ofNullable(emp.getEmpDesigName()).orElse("");

        // Priority: Salutation → Title → Nothing
        String prefix = salutation != null ? salutation : (title != null ? title : "");

        StringBuilder fullName = new StringBuilder();

        if (!prefix.isBlank()) {
            fullName.append(prefix).append(" ");
        }

        fullName.append(name);

        if (includeDesignation && !designation.isBlank()) {
            fullName.append(", ").append(designation);
        }

        return fullName.toString().trim();
    }

}

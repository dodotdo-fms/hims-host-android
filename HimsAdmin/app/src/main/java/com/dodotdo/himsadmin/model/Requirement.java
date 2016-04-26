package com.dodotdo.himsadmin.model;

import java.util.List;

/**
 * Created by Omjoon on 16. 3. 28..
 */
public class Requirement {
    int id;
    Employee senderEmployee;
    String registerTimestamp;
    String contentEnglish;
    String contentArabic;
    String contentSpanish;
    String status;
    Employee wipEmployee;
    String wipTimestamp;
    Employee doneEmployee;
    String doneTimestamp;
    Employee requirementEmployee;
    List<Employee> assignedEmployeeList;

    public int getId() {
        return id;
    }

    public Employee getSenderEmployee() {
        return senderEmployee;
    }

    public String getRegisterTimestamp() {
        return registerTimestamp;
    }

    public String getContentEnglish() {
        return contentEnglish;
    }

    public String getContentArabic() {
        return contentArabic;
    }

    public String getContentSpanish() {
        return contentSpanish;
    }

    public String getStatus() {
        return status;
    }

    public Employee getWipEmployee() {
        return wipEmployee;
    }

    public String getWipTimestamp() {
        return wipTimestamp;
    }

    public Employee getDoneEmployee() {
        return doneEmployee;
    }

    public String getDoneTimestamp() {
        return doneTimestamp;
    }

    public Employee getRequirementEmployee() {
        return requirementEmployee;
    }

    public List<Employee> getAssignedEmployeeList() {
        return assignedEmployeeList;
    }
}

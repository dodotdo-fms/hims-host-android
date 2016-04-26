package com.dodotdo.himsadmin.model;

/**
 * Created by Omjoon on 16. 4. 19..
 */
public class CheckEmployeeStatus {
    Employee employee;
    boolean checked;

    public CheckEmployeeStatus(Employee employee, boolean checked) {
        this.employee = employee;
        this.checked = checked;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

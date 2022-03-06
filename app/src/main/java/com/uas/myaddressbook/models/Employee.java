package com.uas.myaddressbook.models;

public class Employee {
    private Integer employeeId;
    private EmployeeName name;
    private EmployeeLocation location;
    private String email;
    private EmployeeRegistered registered;
    private String phone;
    private String cell;
    private EmployeePicture picture;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public EmployeeName getName() {
        return name;
    }

    public EmployeeLocation getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeRegistered getRegistered() {
        return registered;
    }

    public String getPhone() {
        return phone;
    }

    public String getCell() {
        return cell;
    }

    public EmployeePicture getPicture() {
        return picture;
    }
}

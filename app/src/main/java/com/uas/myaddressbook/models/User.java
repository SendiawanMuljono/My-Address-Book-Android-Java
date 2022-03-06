package com.uas.myaddressbook.models;

import java.util.ArrayList;

public class User {
    private Integer statusCode;
    private String nim;
    private String nama;
    private ArrayList<Employee> employees;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}

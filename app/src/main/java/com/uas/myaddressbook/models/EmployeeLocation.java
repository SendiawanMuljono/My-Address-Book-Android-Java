package com.uas.myaddressbook.models;

public class EmployeeLocation {
    private String city;
    private String country;
    private EmployeeLocationCoordinate coordinates;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public EmployeeLocationCoordinate getCoordinates() {
        return coordinates;
    }
}

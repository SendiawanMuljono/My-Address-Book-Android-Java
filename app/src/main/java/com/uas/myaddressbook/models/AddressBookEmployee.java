package com.uas.myaddressbook.models;

public class AddressBookEmployee {
    private Integer employeeId;
    private String name;
    private String city;
    private String phone;
    private String email;
    private String picture;

    public AddressBookEmployee(Integer employeeId, String name, String city, String phone, String email, String picture) {
        this.employeeId = employeeId;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.picture = picture;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
}

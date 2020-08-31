package com.RMIT;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class Lead {
    @CsvBindByName(column="id", required = true) // Annotations to match with CSV's header.
    private String id;

    @CsvBindByName(column="name", required = true)
    private String name;

    @CsvBindByName(column="DOB", required = true)
    @CsvDate(value="yyyy-MM-dd") // Annotation to parse Date format.
    private Date DOB;

    @CsvBindByName(column="gender", required = true)
    private Boolean gender;

    @CsvBindByName(column="phone", required = true)
    private String phone;

    @CsvBindByName(column="email", required = true)
    private String email;

    @CsvBindByName(column="address", required = true)
    private String address;

    public Lead() {}

    // write object to CSV file
    public Lead(String name, Date DOB, Boolean gender, String phone, String email, String address) {
        this.id = "lead_" + Generator.generateLeadId();
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // parse object from CSV file


    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }
    public String getDOBString() {
        return Converter.DateToStr(this.DOB);
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

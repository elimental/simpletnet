package com.getjavajob.simplenet.entity;

import java.sql.Date;
import java.util.List;

public class Account extends BaseEntity {
    private String firstName;
    private String lastName;
    private String patronymicName;
    private Date birthDay;
    private String homeAddress;
    private String workAddress;
    private String email;
    private String icq;
    private String skype;
    private String additionalInfo;
    private List<String> homePhones;
    private List<String> workPhones;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<String> getHomePhones() {
        return homePhones;
    }

    public void setHomePhones(List<String> homePhones) {
        this.homePhones = homePhones;
    }

    public List<String> getWorkPhones() {
        return workPhones;
    }

    public void setWorkPhones(List<String> workPhones) {
        this.workPhones = workPhones;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymicName='" + patronymicName + '\'' +
                ", birthDay=" + birthDay +
                ", homeAddress='" + homeAddress + '\'' +
                ", workAddress='" + workAddress + '\'' +
                ", email='" + email + '\'' +
                ", icq='" + icq + '\'' +
                ", skype='" + skype + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}

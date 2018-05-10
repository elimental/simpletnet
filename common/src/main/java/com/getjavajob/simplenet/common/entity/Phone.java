package com.getjavajob.simplenet.common.entity;

import java.util.Objects;

public class Phone extends BaseEntity {
    private String number;
    private PhoneType type;
    private int phoneOwner;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public int getPhoneOwner() {
        return phoneOwner;
    }

    public void setPhoneOwner(int phoneOwner) {
        this.phoneOwner = phoneOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return getPhoneOwner() == phone.getPhoneOwner() &&
                Objects.equals(getNumber(), phone.getNumber()) &&
                getType() == phone.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getType(), getPhoneOwner());
    }
}

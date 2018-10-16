package com.getjavajob.simplenet.common.entity;

import java.util.Objects;

public class Phone extends BaseEntity {

    public static final int HOME = 1;
    public static final int WORK = 2;

    private String number;
    private int type;
    private int owner;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", type=" + type +
                ", owner=" + owner +
                '}';
    }
}

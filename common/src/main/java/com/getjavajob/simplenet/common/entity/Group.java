package com.getjavajob.simplenet.common.entity;

import java.sql.Date;
import java.util.Arrays;

public class Group extends BaseEntity {
    private String name;
    private int owner;
    private byte[] picture;
    private Date createDate;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", picture=" + Arrays.toString(picture) +
                ", createDate=" + createDate +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}

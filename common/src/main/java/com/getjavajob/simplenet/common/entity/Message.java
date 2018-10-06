package com.getjavajob.simplenet.common.entity;

import java.sql.Timestamp;

public class Message extends BaseEntity implements Comparable<Message> {
    public static final int WALL = 1;
    public static final int GROUP = 2;
    public static final int PERSONAL = 3;

    private String text;
    private Timestamp createDate;
    private int author;
    private int type;
    private int destination;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    @Override
    public int compareTo(Message o) {
        return -createDate.compareTo(o.getCreateDate());
    }
}

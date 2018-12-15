package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {

    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh.mm");

    @Getter
    @Setter
    private Long from;
    @Getter
    @Setter
    private String fromName;
    @Getter
    @Setter
    private Long to;
    @Getter
    private String date;
    @Getter
    @Setter
    private String text;

    public void setDate(Date date) {
        this.date = df.format(date);
    }
}

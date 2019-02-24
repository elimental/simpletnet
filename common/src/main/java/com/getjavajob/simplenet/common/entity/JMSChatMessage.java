package com.getjavajob.simplenet.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class JMSChatMessage {

    @Getter
    @Setter
    private Long from;
    @Getter
    @Setter
    private Long to;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private String text;
}

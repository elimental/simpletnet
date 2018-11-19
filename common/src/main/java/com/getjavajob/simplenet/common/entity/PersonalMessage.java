package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal_message")
public class PersonalMessage extends BaseEntity implements Comparable<PersonalMessage> {

    @Getter
    @Setter
    @Column(nullable = false, length = 2500)
    private String text;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate;

    @Getter
    @Setter
    @ManyToOne
    private Account from;

    @Getter
    @Setter
    @ManyToOne
    private Account to;

    @Override
    public int compareTo(PersonalMessage o) {
        return -createDate.compareTo(o.getCreateDate());
    }
}

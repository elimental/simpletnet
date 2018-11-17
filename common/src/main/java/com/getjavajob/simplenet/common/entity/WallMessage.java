package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wall_message")
public class WallMessage extends BaseEntity implements Comparable<WallMessage> {

    @Getter
    @Setter
    @Column(nullable = false)
    private String text;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate;

    @Getter
    @Setter
    @ManyToOne
    private Account account;

    @Override
    public int compareTo(WallMessage o) {
        return -createDate.compareTo(o.getCreateDate());
    }
}

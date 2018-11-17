package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "community_message")
public class CommunityMessage extends BaseEntity implements Comparable<CommunityMessage> {

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
    private Community community;

    @Getter
    @Setter
    private Long authorId;

    @Override
    public int compareTo(CommunityMessage o) {
        return -createDate.compareTo(o.getCreateDate());
    }
}

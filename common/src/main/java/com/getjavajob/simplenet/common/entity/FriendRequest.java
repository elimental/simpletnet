package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend_request")
public class FriendRequest extends BaseEntity {

    @Getter
    @Setter
    @ManyToOne
    private Account from;

    @Getter
    @Setter
    @ManyToOne
    private Account to;

    @Getter
    @Setter
    private Boolean accepted = false;

    @Override
    public String toString() {
        return "FriendRequest{" +
                "from=" + from.getId() +
                ", to=" + to.getId() +
                ", accepted=" + accepted +
                ", id=" + id +
                '}';
    }
}

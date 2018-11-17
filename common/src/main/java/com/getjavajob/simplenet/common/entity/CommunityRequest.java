package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.getjavajob.simplenet.common.entity.Role.GROUP_CANDIDATE;

@Entity
@Table(name = "community_request")
public class CommunityRequest extends BaseEntity {

    @Getter
    @Setter
    @ManyToOne
    private Account from;

    @Getter
    @Setter
    @ManyToOne
    private Community community;

    @Getter
    @Setter
    private Boolean accepted = false;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role = GROUP_CANDIDATE;
}

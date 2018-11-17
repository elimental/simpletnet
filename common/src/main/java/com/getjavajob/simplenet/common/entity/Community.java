package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.GROUP_CANDIDATE;
import static java.lang.System.currentTimeMillis;

@Entity
@Table(name = "community")
public class Community extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    private Long creatorId;

    @Getter
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    @Getter
    @Setter
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityRequest> requests = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityMessage> messages;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Getter
    @Setter
    private String description;

    public void updateCommunity(Community updatedCommunity) {
        this.name = updatedCommunity.getName();
        this.description = updatedCommunity.getDescription();
        byte[] picture = updatedCommunity.getPicture();
        if (picture != null) {
            this.picture = picture;
        }
    }

    public void addCommunityMessage(Long authorId, String message) {
        CommunityMessage communityMessage = new CommunityMessage();
        communityMessage.setText(message);
        communityMessage.setCommunity(this);
        communityMessage.setAuthorId(authorId);
        communityMessage.setCreateDate(new Date(currentTimeMillis()));
        this.messages.add(communityMessage);
    }


    public void removeCommunityMessage(long messageId) {
        CommunityMessage communityMessage = new CommunityMessage();
        communityMessage.setId(messageId);
        this.messages.remove(communityMessage);
    }

    public void addRequest(Account account) {
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setFrom(account);
        communityRequest.setCommunity(this);
        communityRequest.setRole(GROUP_CANDIDATE);
        requests.add(communityRequest);
    }
}

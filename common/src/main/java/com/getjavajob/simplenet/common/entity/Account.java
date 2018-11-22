package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date birthDay;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Getter
    @Setter
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    private String icq;

    @Getter
    @Setter
    private String skype;

    @Getter
    @Setter
    private String additionalInfo;

    @Getter
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Setter
    @OneToMany(mappedBy = "phoneOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    @Getter
    @Setter
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommunityRequest> communityRequests;

    @Getter
    @Setter
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private List<PersonalMessage> fromAccount;

    @Getter
    @Setter
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    private List<PersonalMessage> toAccount;

    @Getter
    @Setter
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WallMessage> wallMessages;

    @Getter
    @Setter
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> requestsToOtherAccount;

    @Getter
    @Setter
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> requestsFromOtherAccount;

    public void updateAccount(Account updatedAccount) {
        this.firstName = updatedAccount.getFirstName();
        this.lastName = updatedAccount.getLastName();
        this.birthDay = updatedAccount.getBirthDay();
        this.icq = updatedAccount.getIcq();
        this.skype = updatedAccount.getSkype();
        this.additionalInfo = updatedAccount.getAdditionalInfo();
        byte[] updatedPhoto = updatedAccount.getPhoto();
        if (updatedPhoto != null) {
            this.photo = updatedPhoto;
        }
        List<Phone> updatedPhones = updatedAccount.getPhones();
        this.phones.clear();
        if (updatedPhones != null) {
            this.phones.addAll(updatedPhones);
        }
    }

    public String getAccountFullName() {
        StringBuilder userName = new StringBuilder();
        userName.append(this.getFirstName());
        String lastName = this.getLastName();
        if (lastName != null) {
            userName.append(" ").append(lastName);
        }
        return userName.toString();
    }

    public void addWallMessage(String message) {
        WallMessage wallMessage = new WallMessage();
        wallMessage.setText(message);
        wallMessage.setAccount(this);
        wallMessage.setCreateDate(new Date(currentTimeMillis()));
        this.wallMessages.add(wallMessage);
    }

    public void removeWallMessage(long messageId) {
        WallMessage wallMessage = new WallMessage();
        wallMessage.setId(messageId);
        this.wallMessages.remove(wallMessage);
    }

    public void addFriendRequest(FriendRequest friendRequest) {
        this.requestsToOtherAccount.add(friendRequest);
    }

    public void addPersonalMessage(PersonalMessage personalMessage) {
        this.fromAccount.add(personalMessage);
    }

    @Override
    public String toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }
}

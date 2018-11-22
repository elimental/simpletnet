package com.getjavajob.simplenet.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    private String number;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType type;

    @Getter
    @Setter
    @ManyToOne
    private Account phoneOwner;

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", type=" + type +
                '}';
    }
}

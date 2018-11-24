package com.getjavajob.simplenet.common.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@XStreamAlias("phone")
public class Phone extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    @XStreamAlias("phoneNumber")
    private String number;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @XStreamAlias("phoneType")
    private PhoneType type;

    @Getter
    @Setter
    @ManyToOne
    @XStreamOmitField
    private Account phoneOwner;

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", type=" + type +
                '}';
    }
}

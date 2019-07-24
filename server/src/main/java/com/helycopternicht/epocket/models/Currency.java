package com.helycopternicht.epocket.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data

@Entity
@Table(name = "currencies")
public class Currency extends BaseEntity {
    private String name;

    public com.helycopternicht.epocket.api.Currency getCurrency() {
        return com.helycopternicht.epocket.api.Currency.valueOf(name);
    }
}

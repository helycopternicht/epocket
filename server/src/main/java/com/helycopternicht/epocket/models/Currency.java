package com.helycopternicht.epocket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name = "currencies")
public class Currency extends BaseEntity {
    private String name;
}

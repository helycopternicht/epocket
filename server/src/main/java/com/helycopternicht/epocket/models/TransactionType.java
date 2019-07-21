package com.helycopternicht.epocket.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_types")
public class TransactionType extends BaseEntity {
    private String name;
}

package com.hnyp.axon.connection.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Connection {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private ConnectionState state;

}

package com.hnyp.axon.connection.service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Connection {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private ConnectionState state;

}

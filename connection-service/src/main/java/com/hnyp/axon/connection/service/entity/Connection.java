package com.hnyp.axon.connection.service.entity;

import com.hnyp.axon.api.entity.State;
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
    private String vportId;

    @Column
    private String name;

    @Column
    private State state;

}

package com.hnyp.axon.vport.rest.domain;

import com.hnyp.axon.api.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseVport {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private State state;

}

package com.hnyp.axon.vport.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SagaInfo {

    private String id;
    private BaseVport baseVport;
    private boolean active;

}

package com.hnyp.axon.vport.rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConnectionDetails {

    private String id;

    private String vportId;

    private String name;

    private String status;

}

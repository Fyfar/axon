package com.hnyp.axon.connection.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ConnectionDetails {

    private String id;

    private String vportId;

    private String name;

    private String status;

}

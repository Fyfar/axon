package com.hnyp.axon.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericVportCreatedEvent {

    private String vportId;

    private String genericVportId;

}

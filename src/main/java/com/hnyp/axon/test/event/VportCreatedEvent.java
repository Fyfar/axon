package com.hnyp.axon.test.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VportCreatedEvent {

    private String uuid;

}

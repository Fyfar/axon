package com.hnyp.axon.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkGenericVportFailedCommand {

    private String id;

    private String vportId;

}

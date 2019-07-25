package com.helycopternicht.epocket.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("client-settings")
public class ClientSettingsPropertyHolder {

    private Integer numberOfUsers;
    private Integer numberOfUserThreads;
    private Integer numberOfRoundsPerThread;
}

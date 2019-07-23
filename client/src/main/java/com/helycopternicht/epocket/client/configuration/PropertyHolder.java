package com.helycopternicht.epocket.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("grpc-server")
public class PropertyHolder {

    private String host;
    private Integer port;

}

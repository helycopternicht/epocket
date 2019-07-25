package com.helycopternicht.epocket.configuration;

import com.helycopternicht.epocket.endpoints.WalletEndpoint;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GRPCConfiguration {

    @Value("${grpc-server.port}")
    private Integer port;

    @Bean
    public Server grpcServer(WalletEndpoint walletEndpoint) throws IOException {
        return ServerBuilder
                .forPort(port)
                .addService(walletEndpoint)
                .build()
                .start();
    }

}

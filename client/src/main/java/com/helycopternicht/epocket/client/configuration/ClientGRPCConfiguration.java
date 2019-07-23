package com.helycopternicht.epocket.client.configuration;

import com.helycopternicht.epocket.api.WalletServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientGRPCConfiguration {

    private final PropertyHolder holder;

    @Bean
    public ManagedChannel channel() {
        return ManagedChannelBuilder
                .forAddress(holder.getHost(), holder.getPort())
                .usePlaintext()
                .build();
    }

    @Bean
    public WalletServiceGrpc.WalletServiceBlockingStub walletService(ManagedChannel channel) {
        return WalletServiceGrpc.newBlockingStub(channel);
    }

}


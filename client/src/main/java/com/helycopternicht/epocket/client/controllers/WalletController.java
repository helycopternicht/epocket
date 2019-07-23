package com.helycopternicht.epocket.client.controllers;

import com.helycopternicht.epocket.client.services.WalletService;
import com.helycopternicht.epocket.client.services.dtos.TransactionRequestDto;
import com.helycopternicht.epocket.client.services.dtos.UserBalanceRequestDto;
import com.helycopternicht.epocket.client.services.dtos.UserBalanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/deposit")
    public void doDeposit(@RequestBody TransactionRequestDto request) {
        walletService.doDeposit(request);
    }

    @PostMapping("/withdraw")
    public void doWithdraw(@RequestBody TransactionRequestDto request) {
        walletService.doWithdraw(request);
    }

    @PostMapping("/balance")
    public UserBalanceResponseDto doWithdraw(@RequestBody UserBalanceRequestDto request) {
        return UserBalanceResponseDto.from(walletService.getUserBalance(request));
    }

}

package com.helycopternicht.epocket;

import com.helycopternicht.epocket.api.*;
import com.helycopternicht.epocket.controllers.WalletController;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@DirtiesContext
@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(value = "/database/clear_database.sql")
@TestPropertySource(locations = "/application-test.properties")
public class IntegrationTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Autowired
    private WalletController walletController;

    private WalletServiceGrpc.WalletServiceBlockingStub blockingStub;

    @Before
    public void setUp() throws Exception {

        String serverName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(walletController).build().start());

        blockingStub = WalletServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );
    }

    @Test
    public void integrationTestFullStory() {

        long userId = 1L;

        //Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        makeWithdrawalOf200USD_MustThrowsInsufficientException(userId);

        //Make a deposit of USD 100 to user with id 1.
        doDeposit(userId, Currency.USD, 100.);

        //Check that all balances are correct
        balance_MustHaveOnly100USD(userId);

        //Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        makeWithdrawalOf200USD_MustThrowsInsufficientException(userId);

        //Make a deposit of EUR 100 to user with id 1.
        doDeposit(userId, Currency.EUR, 100.);

        //Check that all balances are correct
        balance_MustHave_100USD_AND_100EUR_AtThatMoment(userId);

        //Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        makeWithdrawalOf200USD_MustThrowsInsufficientException(userId);

        //Make a deposit of USD 100 to user with id 1.
        doDeposit(userId, Currency.USD, 100.);

        //Check that all balances are correct
        balanceMustHave_200USD_and_100EUR_AtThatMoment(userId);

        //Make a withdrawal of USD 200 for user with id 1. Must return "ok".
        makeWithdrawalOf200USD_MustBeSuccessful(userId);

        //Check that all balances are correct
        balance_MustHave_100EUR_And_0USD(userId);

        //Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        makeWithdrawalOf200USD_MustThrowsInsufficientException(userId);
    }

    private void balance_MustHave_100EUR_And_0USD(long userId) {
        UserBalanceResponse userBalance;
        CurrencyBalance eurBalance;
        CurrencyBalance usdBalance;
        userBalance = getBalance(userId);

        eurBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.EUR).findFirst().get();

        usdBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.USD).findFirst().get();

        assertEquals(2, userBalance.getBalancesCount());
        assertEquals(100., eurBalance.getBalance(), 0.);
        assertEquals(0., usdBalance.getBalance(), 0.);
    }

    private void makeWithdrawalOf200USD_MustBeSuccessful(long userId) {
        StatusRuntimeException exception;
        exception = null;
        try {
            doWithdraw(userId, Currency.USD, 200.);
        } catch (StatusRuntimeException ex) {
            exception =  ex;
        }

        assertNull(exception);
    }

    private void balanceMustHave_200USD_and_100EUR_AtThatMoment(long userId) {
        UserBalanceResponse userBalance;
        CurrencyBalance eurBalance;
        CurrencyBalance usdBalance;
        userBalance = getBalance(userId);

        eurBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.EUR).findFirst().get();

        usdBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.USD).findFirst().get();

        assertEquals(2, userBalance.getBalancesCount());
        assertEquals(100., eurBalance.getBalance(), 0.);
        assertEquals(200., usdBalance.getBalance(), 0.);
    }

    private void balance_MustHave_100USD_AND_100EUR_AtThatMoment(long userId) {
        UserBalanceResponse userBalance = getBalance(userId);

        CurrencyBalance eurBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.EUR).findFirst().get();

        CurrencyBalance usdBalance = userBalance.getBalancesList().stream()
                .filter(el -> el.getCurrency() == Currency.USD).findFirst().get();

        assertEquals(2, userBalance.getBalancesCount());
        assertEquals(100., eurBalance.getBalance(), 0.);
        assertEquals(100., usdBalance.getBalance(), 0.);
    }

    private void balance_MustHaveOnly100USD(long userId) {
        UserBalanceResponse userBalance = getBalance(userId);
        assertEquals(1, userBalance.getBalancesCount());
        assertEquals(Currency.USD, userBalance.getBalances(0).getCurrency());
        assertEquals(100., userBalance.getBalances(0).getBalance(), 0.);
    }

    private void makeWithdrawalOf200USD_MustThrowsInsufficientException(long userId) {
        StatusRuntimeException exception = null;
        try {
            doWithdraw(userId, Currency.USD, 200.);
        } catch (StatusRuntimeException ex) {
            exception =  ex;
        }
        assertNotNull(exception);
        assertEquals(Status.Code.INTERNAL, exception.getStatus().getCode());
        assertEquals("Insufficient fonds", exception.getStatus().getDescription());
    }

    private void doDeposit(long userId, Currency currency, double sum) {
        blockingStub.doDeposit(
                TransactionRequest.newBuilder()
                        .setUserId(userId)
                        .setCurrency(currency)
                        .setAmount(sum)
                        .build()
        );
    }

    private void doWithdraw(long userId, Currency currency, double sum) throws StatusRuntimeException {
        blockingStub.doWithdraw(
                TransactionRequest.newBuilder()
                        .setUserId(userId)
                        .setCurrency(currency)
                        .setAmount(sum)
                        .build()
        );
    }

    private UserBalanceResponse getBalance(long userId) {
        return blockingStub.getUserBalance(UserBalanceRequest.newBuilder().setUserId(userId).build());
    }
}

package com.sanjay.client;

import com.sanjay.models.Balance;
import com.sanjay.models.BalanceCheckRequest;
import com.sanjay.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void balanceTest(){
        BalanceCheckRequest balanceCheckRequest =
                BalanceCheckRequest.newBuilder()
                        .setAccountNumber(1)
                        .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);

        int amount = balance.getAmount();
        System.out.println("Received amount:" + amount);
        assert amount == 10;
    }

}

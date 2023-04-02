package com.sanjay.server;


import com.sanjay.models.Balance;
import com.sanjay.models.BalanceCheckRequest;
import com.sanjay.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        super.getBalance(request, responseObserver);
    }
}

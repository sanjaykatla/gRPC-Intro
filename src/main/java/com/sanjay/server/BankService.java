package com.sanjay.server;


import com.sanjay.models.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountsDatabase.getBalance(accountNumber))
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();

        int balance = AccountsDatabase.getBalance(accountNumber);

        if(balance < amount){
            Status status = Status.FAILED_PRECONDITION.withDescription("No enough money." +
                    "You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        // all validation passed
        for(int i = 0; i< (amount / 10); i++){
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountsDatabase.deductBalance(accountNumber, 10);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}

package com.sainnt.account.cmd.domain;

import com.sainnt.account.cmd.api.command.CloseAccountCommand;
import com.sainnt.account.cmd.api.command.DepositFundsCommand;
import com.sainnt.account.cmd.api.command.OpenAccountCommand;
import com.sainnt.account.cmd.api.command.WithdrawFundsCommand;
import com.sainnt.account.cmd.api.event.AccountClosedEvent;
import com.sainnt.account.cmd.api.event.AccountOpenedEvent;
import com.sainnt.account.cmd.api.event.FundsDepositedEvent;
import com.sainnt.account.cmd.api.event.FundsWithdrawnEvent;
import com.sainnt.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean isActive;
    private long balance;
    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent
                .builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getType())
                .openingBalance(command.getOpeningBalance())
                .creationDate(new Date())
                .build());
    }

    private void apply(AccountOpenedEvent event){
        id = event.getId();
        this.balance = event.getOpeningBalance();
        this.isActive = true;
    }

    public void depositFunds(DepositFundsCommand command)
    {
        if(!isActive)
            throw new IllegalStateException("Funds cannot be deposited into closed account");
        if(command.getAmount()<0)
            throw  new IllegalStateException("Deposit amount cannot be less then 0");
        raiseEvent(FundsDepositedEvent
                .builder()
                .id(command.getId())
                .amount(command.getAmount())
                .build());
    }

    private void apply(FundsDepositedEvent event){
        id = event.getId();
        this.balance += event.getAmount();
    }
    public void withdrawFunds(WithdrawFundsCommand command)
    {
        if(!isActive)
            throw new IllegalStateException("Funds cannot be withdrawn from closed account");
        if(command.getAmount()<0)
            throw  new IllegalStateException("Withdraw amount cannot be less then 0");
        if(command.getAmount()> balance)
            throw new IllegalStateException("Withdraw amount cannot be greater then balance");
        raiseEvent(FundsWithdrawnEvent
                .builder()
                .id(command.getId())
                .amount(command.getAmount())
                .build());
    }

    private void apply(FundsWithdrawnEvent event){
        id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(CloseAccountCommand command){
        if(!isActive)
            throw new IllegalStateException("Account is already closed");
        raiseEvent(AccountClosedEvent
                .builder()
                .id(command.getId())
                        .build()
                );
    }
    private void apply(AccountClosedEvent event){
        id = event.getId();
        isActive = false;
    }



}

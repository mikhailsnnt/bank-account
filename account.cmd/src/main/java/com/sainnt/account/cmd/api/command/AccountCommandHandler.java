package com.sainnt.account.cmd.api.command;

import com.sainnt.account.cmd.domain.AccountAggregate;
import com.sainnt.cqrs.core.handler.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler{
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Autowired
    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }


    @Override
    public void handle(OpenAccountCommand command) {
        AccountAggregate aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.withdrawFunds(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount(command);
        eventSourcingHandler.save(aggregate);
    }
}

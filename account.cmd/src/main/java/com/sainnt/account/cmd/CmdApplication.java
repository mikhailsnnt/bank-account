package com.sainnt.account.cmd;

import com.sainnt.account.cmd.api.command.*;
import com.sainnt.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CmdApplication {
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    @Autowired
    public CmdApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.commandHandler = commandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(CmdApplication.class, args);
    }


    @PostConstruct
    public void postConstruct(){
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
    }

}

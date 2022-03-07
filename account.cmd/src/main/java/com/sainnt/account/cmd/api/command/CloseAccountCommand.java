package com.sainnt.account.cmd.api.command;

import com.sainnt.cqrs.core.command.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id){
        super(id);
    }
}

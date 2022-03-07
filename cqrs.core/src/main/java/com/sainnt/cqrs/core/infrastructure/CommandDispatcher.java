package com.sainnt.cqrs.core.infrastructure;

import com.sainnt.cqrs.core.command.BaseCommand;
import com.sainnt.cqrs.core.command.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}

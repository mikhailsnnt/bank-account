package com.sainnt.account.cmd.infastructure;

import com.sainnt.cqrs.core.command.BaseCommand;
import com.sainnt.cqrs.core.command.CommandHandlerMethod;
import com.sainnt.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, CommandHandlerMethod> routes = new HashMap<>();
    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        if(routes.containsKey(type))
            throw new RuntimeException("Handler is already registered for "+type.getName());
        routes.put(type,handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handler = routes.get(command.getClass());
        if(handler == null)
            throw new RuntimeException("Handler is not found for "+command.getClass().getName());
        handler.handle(command);
    }
}

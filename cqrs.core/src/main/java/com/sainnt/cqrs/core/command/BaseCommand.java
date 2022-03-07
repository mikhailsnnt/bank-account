package com.sainnt.cqrs.core.command;

import com.sainnt.cqrs.core.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseCommand extends Message {
    public BaseCommand(String id){
        super(id);
    }
}

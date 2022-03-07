package com.sainnt.account.cmd.api.command;

import com.sainnt.account.common.dto.AccountType;
import com.sainnt.cqrs.core.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType type;
    private long openingBalance;
}

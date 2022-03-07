package com.sainnt.account.cmd.api.command;

import com.sainnt.cqrs.core.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawFundsCommand extends BaseCommand {
    private long amount;
}

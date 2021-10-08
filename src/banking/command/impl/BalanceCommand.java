package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public class BalanceCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public BalanceCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.checkBalance();
    }
}

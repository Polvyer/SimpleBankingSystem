package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public final class LogIntoAccountCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public LogIntoAccountCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.logIntoAccount();
    }
}

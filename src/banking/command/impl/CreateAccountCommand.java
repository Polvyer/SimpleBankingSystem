package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public final class CreateAccountCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public CreateAccountCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.createAccount();
    }
}

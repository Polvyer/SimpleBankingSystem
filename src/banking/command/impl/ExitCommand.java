package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public final class ExitCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public ExitCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.exit();
    }
}

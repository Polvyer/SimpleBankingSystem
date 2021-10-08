package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public class CloseAccountCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public CloseAccountCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.closeAccount();
    }
}

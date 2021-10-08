package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public class LogoutCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public LogoutCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.logOut();
    }
}

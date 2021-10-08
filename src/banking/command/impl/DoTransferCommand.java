package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public class DoTransferCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public DoTransferCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.doTransfer();
    }
}

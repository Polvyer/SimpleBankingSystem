package banking.command.impl;

import banking.command.Command;
import banking.service.BankingAccountService;

public class AddIncomeCommand implements Command {

    private final BankingAccountService bankingAccountService;

    public AddIncomeCommand(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public void execute() {
        bankingAccountService.addIncome();
    }
}

package banking.factory;

import banking.command.Command;
import banking.command.impl.*;
import banking.service.BankingAccountService;

import static banking.util.BankingSystemConstants.*;

public final class CommandFactory {

    private BankingAccountService bankingAccountService;

    public CommandFactory(final BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    public Command createCommand(String type) {
        switch(type) {
            case EXIT:
                return new ExitCommand(bankingAccountService);
            case CREATE_AN_ACCOUNT:
                return new CreateAccountCommand(bankingAccountService);
            case LOG_INTO_ACCOUNT:
                return new LogIntoAccountCommand(bankingAccountService);
            case LOG_OUT:
                return new LogoutCommand(bankingAccountService);
            case BALANCE:
                return new BalanceCommand(bankingAccountService);
            case ADD_INCOME:
                return new AddIncomeCommand(bankingAccountService);
            case CLOSE_ACCOUNT:
                return new CloseAccountCommand(bankingAccountService);
            case DO_TRANSFER:
                return new DoTransferCommand(bankingAccountService);
            default:
                throw new RuntimeException("Sorry, we are not able to create this kind of command\n");
        }
    }
}

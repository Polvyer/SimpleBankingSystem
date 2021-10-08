package banking;

import banking.command.Command;
import banking.command.CommandController;
import banking.factory.CommandFactory;
import banking.service.BankingMenuService;

public final class BankingSystem {

    private final BankingMenuService bankingMenuService;
    private final CommandFactory commandFactory;
    private final CommandController commandController;

    public BankingSystem(final BankingMenuService bankingMenuService,
                         final CommandFactory commandFactory) {
        this.bankingMenuService = bankingMenuService;
        this.commandFactory = commandFactory;
        this.commandController = new CommandController();
    }

    public void start() {
        while (true) {
            final String commandType = bankingMenuService.getCommandTypeFromUser();
            final Command command = commandFactory.createCommand(commandType);
            commandController.setCommand(command);
            commandController.executeCommand();
        }
    }
}

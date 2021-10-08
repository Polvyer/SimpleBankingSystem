package banking.factory;

import banking.BankingSystem;
import banking.dto.Menu;
import banking.dto.MenuOption;
import banking.service.BankingAccountService;
import banking.service.BankingMenuService;
import banking.service.impl.BankingAccountServiceImpl;
import banking.service.impl.BankingMenuServiceImpl;
import banking.ui.UserInterface;

import java.util.Scanner;

import static banking.util.BankingSystemConstants.*;

public final class BankingSystemFactory {

    private static BankingSystemFactory instance;

    private BankingSystemFactory() {
    }

    public static BankingSystemFactory getInstance() {
        if (instance == null) {
            instance = new BankingSystemFactory();
        }
        return instance;
    }

    public BankingSystem createBankingSystem() {
        final UserInterface ui = createUI();
        final Menu defaultMenu = createDefaultMenu();
        final BankingMenuService bankingMenuService = createBankingMenuService(ui, defaultMenu);
        final CommandFactory commandFactory = createCommandFactory(ui, bankingMenuService);
        return new BankingSystem(bankingMenuService, commandFactory);
    }

    private Menu createDefaultMenu() {
        final Menu logIntoAccountSubMenu = Menu.of(
                new MenuOption(EXIT),
                new MenuOption(BALANCE),
                new MenuOption(ADD_INCOME),
                new MenuOption(DO_TRANSFER),
                new MenuOption(CLOSE_ACCOUNT),
                new MenuOption(LOG_OUT)
        );

        return Menu.of(
                new MenuOption(EXIT),
                new MenuOption(CREATE_AN_ACCOUNT),
                new MenuOption(LOG_INTO_ACCOUNT, logIntoAccountSubMenu)
        );
    }

    private UserInterface createUI() {
        final Scanner scanner = new Scanner(System.in);
        return new UserInterface(scanner);
    }

    private BankingMenuService createBankingMenuService(final UserInterface ui,
                                                        final Menu defaultMenu) {
        return new BankingMenuServiceImpl(ui, defaultMenu);
    }

    private CommandFactory createCommandFactory(final UserInterface ui,
                                                final BankingMenuService bankingMenuService) {
        final BankingAccountService bankingAccountService
                = new BankingAccountServiceImpl(ui, bankingMenuService);
        return new CommandFactory(bankingAccountService);
    }
}

package banking.service.impl;

import banking.dto.Menu;
import banking.dto.MenuOption;
import banking.service.BankingMenuService;
import banking.ui.UserInterface;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class BankingMenuServiceImpl implements BankingMenuService {

    private final UserInterface ui;
    private Menu currentMenu;
    private MenuOption currentMenuOption;
    private Deque<Menu> menuStack;

    public BankingMenuServiceImpl(final UserInterface ui,
                                  final Menu defaultMenu) {
        this.ui = ui;
        this.currentMenu = defaultMenu;
        this.menuStack = new ArrayDeque<>();
    }

    @Override
    public String getCommandTypeFromUser() {
        final int userChoice = ui.getUserChoiceFromMenu(currentMenu);
        final MenuOption menuOption = currentMenu.getMenuOptionFromUserChoice(userChoice);
        setCurrentMenuOption(menuOption);
        return menuOption.getText();
    }

    @Override
    public void traverseSubMenuOfCurrentOption() {
        final Optional<Menu> subMenuOptional = currentMenuOption.getSubMenu();
        subMenuOptional.ifPresent(subMenu -> {
            menuStack.addFirst(currentMenu);
            setCurrentMenu(subMenu);
        });
    }

    @Override
    public void revertToPreviousMenu() {
        if (menuStack.isEmpty()) {
            return;
        }
        final Menu previousMenu = menuStack.removeFirst();
        setCurrentMenu(previousMenu);
    }

    private void setCurrentMenuOption(final MenuOption menuOption) {
        this.currentMenuOption = menuOption;
    }

    private void setCurrentMenu(final Menu menu) {
        this.currentMenu = menu;
    }
}

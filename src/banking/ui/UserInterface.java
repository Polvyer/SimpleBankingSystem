package banking.ui;

import banking.dto.Menu;

import java.util.Scanner;

public final class UserInterface {

    private final Scanner scanner;

    public UserInterface(final Scanner scanner) {
        this.scanner = scanner;
    }

    public void printTextWithDoubleNewline(final String text) {
        printTextWithNewline(text);
        printLineSeparator();
    }

    public void printLineSeparator() {
        System.out.print(System.lineSeparator());
    }

    public int getUserChoiceFromMenu(final Menu menu) {
        printMenu(menu);
        return askForMenuOption();
    }

    public String getUserInputFromText(final String text) {
        printTextWithNewline(text);
        return getInput();
    }

    public void printTextWithNewline(final String text) {
        System.out.println(text);
    }

    private void printMenu(final Menu menu) {
        System.out.println(menu);
    }

    private int askForMenuOption() {
        final String input = getInput();
        final int menuOption = Integer.parseInt(input);
        printLineSeparator();
        return menuOption;
    }

    private String getInput() {
        System.out.print(">");
        return scanner.next();
    }
}

package banking.service.impl;

import banking.dto.Account;
import banking.service.BankingAccountService;
import banking.service.BankingMenuService;
import banking.ui.UserInterface;

import java.util.*;

import static banking.util.BankingSystemConstants.*;

public final class BankingAccountServiceImpl extends BankingAccountService {

    private static int nextAvailableCustomerAccountNumber = 1;

    public BankingAccountServiceImpl(final UserInterface ui,
                                     final BankingMenuService bankingMenuService) {
        super(ui, bankingMenuService);
    }

    @Override
    public void createAccount() {
        final String cardNumber = generateNewValidCardNumber();
        final String pinCode = generateRandomPinCode();
        final Account account = new Account(cardNumber, pinCode);
        addAccount(account);
        ui.printTextWithNewline("Your card has been created");
        ui.printTextWithNewline(account.toString());
    }

    @Override
    public void logIntoAccount() {
        final Account credentials = getAccountCredentials();
        final Optional<Account> accountOptional = findAccountByCredentials(credentials);
        validateCredentials(accountOptional);
        ui.printLineSeparator();
    }

    @Override
    public void exit() {
        ui.printTextWithNewline("Bye!");
        System.exit(0);
    }

    @Override
    public void logOut() {
        logOutAndPrintMessage("You have successfully logged out!");
    }

    @Override
    public void checkBalance() {
        final Account account = getCurrentAccount();
        ui.printTextWithDoubleNewline(String.format("Balance: %d", account.getBalance()));
    }

    @Override
    public void doTransfer() {
        ui.printTextWithNewline("Transfer");
        final String cardNumber = ui.getUserInputFromText("Enter card number:");
        final Account currentAccount = getCurrentAccount();

        if (cardNumber.equals(currentAccount.getCardNumber())) {
            ui.printTextWithDoubleNewline("You can't transfer money to the same account!");
        } else if (cardNumberIsInvalid(cardNumber)) {
            ui.printTextWithDoubleNewline("Probably you made a mistake in the card number. Please try again!");
        } else {
            final Optional<Account> accountOptional = findAccountByCardNumber(cardNumber);
            accountOptional.ifPresentOrElse(
                account -> getAmountAndPerformTransfer(account),
                () -> ui.printTextWithDoubleNewline("Such a card does not exist.")
            );
        }
    }

    private void getAmountAndPerformTransfer(final Account receiverAccount) {
        final int amountToTransfer
                = Integer.parseInt(ui.getUserInputFromText("Enter how much money you want to transfer:"));
        if (amountToTransfer > getCurrentAccount().getBalance()) {
            ui.printTextWithDoubleNewline("Not enough money!");
            return;
        }
        final boolean success = transferAccountIncome(receiverAccount, amountToTransfer);
        if (!success) {
            ui.printTextWithDoubleNewline("Unsuccessful");
            return;
        }
        final int amountToTakeAway = amountToTransfer * -1;
        updateCurrentAccountIncome(amountToTakeAway);
        ui.printTextWithDoubleNewline("Success!");
    }

    @Override
    public void addIncome() {
        final int income = Integer.parseInt(ui.getUserInputFromText("Enter income:"));
        final boolean success = updateAccountIncome(getCurrentAccount(), income);
        if (!success) {
            ui.printTextWithDoubleNewline("Income was not added!");
            return;
        }
        updateCurrentAccountIncome(income);
        ui.printTextWithDoubleNewline("Income was added!");
    }

    @Override
    public void closeAccount() {
        final boolean accountDeleted = deleteCurrentAccount();
        if (accountDeleted) {
            logOutAndPrintMessage("The account has been closed!");
        }
    }

    private void validateCredentials(final Optional<Account> accountOptional) {
        accountOptional.ifPresentOrElse(
            account -> {
                ui.printTextWithNewline("You have successfully logged in!");
                setCurrentAccount(account);
                bankingMenuService.traverseSubMenuOfCurrentOption();
            },
            () -> ui.printTextWithNewline("Wrong card number or PIN!")
        );
    }

    private Account getAccountCredentials() {
        final String cardNumber = ui.getUserInputFromText("Enter your card number:");
        final String pinCode = ui.getUserInputFromText("Enter your PIN:");
        ui.printLineSeparator();
        return new Account(cardNumber, pinCode);
    }

    private String generateNewValidCardNumber() {
        String newCardNumber;
        do {
            newCardNumber = generateNewCardNumber();
        } while(cardNumberIsInvalid(newCardNumber));
        return newCardNumber;
    }

    private String generateNewCardNumber() {
        final StringBuilder newCardNumber = new StringBuilder();
        final String customerAccountNumber =
                getAndUpateNextAvailableCustomerAccountNumber();
        newCardNumber.append(BANK_IDENTIFICATION_NUMBER)
                .append(customerAccountNumber)
                .append(CHECK_DIGIT);
        return newCardNumber.toString();
    }

    private String getAndUpateNextAvailableCustomerAccountNumber() {
        return formatNumber(CUSTOMER_ACCOUNT_NUMBER_LENGTH,
                nextAvailableCustomerAccountNumber++);
    }

    private String generateRandomPinCode() {
        final Random random = new Random();
        return formatNumber(PIN_CODE_LENGTH, random.nextInt(PIN_CODE_BOUND));
    }

    private String formatNumber(final int width, final int bound) {
        return String.format("%0" + width + "d", bound);
    }

    private void logOutAndPrintMessage(final String message) {
        setCurrentAccount(null);
        bankingMenuService.revertToPreviousMenu();
        ui.printTextWithDoubleNewline(message);
    }
}

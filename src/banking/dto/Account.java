package banking.dto;

import java.util.Objects;

public final class Account {

    private final String cardNumber;
    private final String pinCode;
    private int balance;

    public Account(final String cardNumber, final String pinCode) {
        this(cardNumber, pinCode, 0);
    }

    public Account(final String cardNumber, final String pinCode, final int balance) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        StringBuilder account = new StringBuilder();
        account.append("Your card number:")
                .append(System.lineSeparator())
                .append(cardNumber)
                .append(System.lineSeparator())
                .append("Your card PIN:")
                .append(System.lineSeparator())
                .append(pinCode)
                .append(System.lineSeparator());
        return account.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(cardNumber, account.cardNumber) && Objects.equals(pinCode, account.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, pinCode);
    }
}

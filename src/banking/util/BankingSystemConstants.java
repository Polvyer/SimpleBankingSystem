package banking.util;

public final class BankingSystemConstants {

    public final static int MAJOR_INDUSTRY_IDENTIFIER = 4;
    public final static int BANK_IDENTIFICATION_NUMBER = 400_000;
    public final static int CHECK_DIGIT = 1;
    public final static int CUSTOMER_ACCOUNT_NUMBER_LENGTH = 9;
    public final static int PIN_CODE_LENGTH = 4;
    public final static int PIN_CODE_BOUND = 10_000;
    public final static String CREATE_AN_ACCOUNT = "Create an Account";
    public final static String LOG_INTO_ACCOUNT = "Log into account";
    public final static String EXIT = "Exit";
    public final static String BALANCE = "Balance";
    public final static String LOG_OUT = "Log out";
    public final static String ADD_INCOME = "Add income";
    public final static String DO_TRANSFER = "Do transfer";
    public final static String CLOSE_ACCOUNT = "Close account";
    public final static String SQL_INSERT_INTO_CARD = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
    public final static String SQL_FIND_CARD_BY_CREDENTIALS = "SELECT * FROM card WHERE number = ? AND pin = ?";
    public final static String SQL_FIND_CARD_BY_NUMBER = "SELECT * FROM card WHERE number = ?";
    public final static String SQL_UPDATE_CARD_INCOME = "UPDATE card SET balance = ? WHERE number = ?";
    public final static String SQL_DELETE_CARD_BY_NUMBER = "DELETE FROM card WHERE number = ?";
    public final static String SQL_CARD_TABLE_NUMBER_COLUMN = "number";
    public final static String SQL_CARD_TABLE_PIN_COLUMN = "pin";
    public final static String SQL_CARD_TABLE_BALANCE_COLUMN = "balance";
    public final static String SQL_CREATE_CARD_TABLE = "CREATE TABLE IF NOT EXISTS card (id INTEGER PRIMARY KEY, number TEXT, pin TEXT, balance INTEGER)";
}

package banking.service;

import banking.algorithm.LuhnAlgorithm;
import banking.algorithm.impl.LuhnAlgorithmImpl;
import banking.dto.Account;
import banking.ui.UserInterface;
import banking.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static banking.util.BankingSystemConstants.*;

public abstract class BankingAccountService {

    protected final UserInterface ui;
    protected final BankingMenuService bankingMenuService;
    private Optional<Account> currentAccount;
    private LuhnAlgorithm luhnAlgorithm;

    public BankingAccountService(final UserInterface ui,
                                 final BankingMenuService bankingMenuService) {
        this.ui = ui;
        this.bankingMenuService = bankingMenuService;
        this.currentAccount = Optional.empty();
        this.luhnAlgorithm = new LuhnAlgorithmImpl();
    }

    public abstract void createAccount();

    public abstract void logIntoAccount();

    public abstract void exit();

    public abstract void logOut();

    public abstract void checkBalance();

    public abstract void doTransfer();

    public abstract void addIncome();

    public abstract void closeAccount();

    protected boolean deleteCurrentAccount() {
        final Connection con = Database.getConnection();
        try (final PreparedStatement preparedStatement
                     = con.prepareStatement(SQL_DELETE_CARD_BY_NUMBER)) {
            final Account account = getCurrentAccount();
            preparedStatement.setString(1, account.getCardNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (final SQLException e) {
            return false;
        }
    }

    protected boolean transferAccountIncome(final Account receiverAccount,
                                         final int amountToTransfer) {
        final Connection con = Database.getConnection();
        try (final PreparedStatement receiverStatement
                     = con.prepareStatement(SQL_UPDATE_CARD_INCOME);
             final PreparedStatement currentAccountStatement
                     = con.prepareStatement(SQL_UPDATE_CARD_INCOME)) {
            con.setAutoCommit(false);
            buildAndExecutePreparedStatementForUpdatingCardIncome(receiverStatement,
                    receiverAccount, amountToTransfer);
            final int amountToTakeAway = amountToTransfer * -1;
            buildAndExecutePreparedStatementForUpdatingCardIncome(currentAccountStatement,
                    getCurrentAccount(), amountToTakeAway);
            con.commit();
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void buildAndExecutePreparedStatementForUpdatingCardIncome(final PreparedStatement preparedStatement,
                                                                       final Account account,
                                                                       final int income)
            throws SQLException {
        final int newBalance = account.getBalance() + income;
        preparedStatement.setInt(1, newBalance);
        preparedStatement.setString(2, account.getCardNumber());
        preparedStatement.executeUpdate();
    }

    protected boolean updateAccountIncome(final Account account, final int income) {
        final Connection con = Database.getConnection();
        try (final PreparedStatement preparedStatement
                     = con.prepareStatement(SQL_UPDATE_CARD_INCOME)) {
            buildAndExecutePreparedStatementForUpdatingCardIncome(preparedStatement, account, income);
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void updateCurrentAccountIncome(final int income) {
        final Account currentAccount = getCurrentAccount();
        final Account updatedAccount
                = new Account(currentAccount.getCardNumber(),
                currentAccount.getPinCode(),
                currentAccount.getBalance() + income);
        setCurrentAccount(updatedAccount);
    }

    protected void addAccount(final Account account) {
        final Connection con = Database.getConnection();
        try (final PreparedStatement preparedStatement
                     = con.prepareStatement(SQL_INSERT_INTO_CARD)) {
            preparedStatement.setString(1, account.getCardNumber());
            preparedStatement.setString(2, account.getPinCode());
            preparedStatement.setInt(3, account.getBalance());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    protected Optional<Account> findAccountByCardNumber(final String cardNumber) {
        final Connection con = Database.getConnection();
        try (final PreparedStatement preparedStatement
                     = con.prepareStatement(SQL_FIND_CARD_BY_NUMBER)) {
            preparedStatement.setString(1, cardNumber);
            final ResultSet resultSet = preparedStatement.executeQuery();
            return getAccountFromResultSet(resultSet);
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    protected Optional<Account> findAccountByCredentials(final Account credentials) {
        final Connection con = Database.getConnection();
        try (final PreparedStatement preparedStatement
                     = con.prepareStatement(SQL_FIND_CARD_BY_CREDENTIALS)) {
            preparedStatement.setString(1, credentials.getCardNumber());
            preparedStatement.setString(2, credentials.getPinCode());
            final ResultSet resultSet = preparedStatement.executeQuery();
            return getAccountFromResultSet(resultSet);
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    protected void setCurrentAccount(final Account account) {
        this.currentAccount = Optional.ofNullable(account);
    }

    protected Account getCurrentAccount() {
        if (!currentAccount.isPresent()) {
            throw new RuntimeException("You need to login!");
        }
        return currentAccount.get();
    }

    protected boolean cardNumberIsInvalid(final String cardNumber) {
        return !luhnAlgorithm.validateCreditCardNumber(cardNumber);
    }

    private Optional<Account> getAccountFromResultSet(final ResultSet resultSet) throws SQLException {
        final String cardNumber = resultSet.getString(SQL_CARD_TABLE_NUMBER_COLUMN);
        final String pinCode = resultSet.getString(SQL_CARD_TABLE_PIN_COLUMN);
        final int balance = resultSet.getInt(SQL_CARD_TABLE_BALANCE_COLUMN);
        final Account accountFound = new Account(cardNumber, pinCode, balance);
        return Optional.ofNullable(accountFound);
    }
}

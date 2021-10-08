package banking.util;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static banking.util.BankingSystemConstants.SQL_CREATE_CARD_TABLE;

public class Database {

    private static SQLiteDataSource dataSource;

    public static void init(final String pathToDatabase) {
        dataSource = new SQLiteDataSource();
        final String url
                = String.format("jdbc:sqlite:%s", pathToDatabase);
        System.out.println(url);
        dataSource.setUrl(url);
        createTableIfItDoesNotExist();
    }

    public static void createTableIfItDoesNotExist() {
        try (final PreparedStatement preparedStatement
                     = getConnection().prepareStatement(SQL_CREATE_CARD_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
    }
}

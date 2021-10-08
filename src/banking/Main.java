package banking;

import banking.factory.BankingSystemFactory;
import banking.util.Database;

public final class Main {

    public static void main(String[] args) {
        Database.init(args[1]);
        final BankingSystemFactory bankingSystemFactory = BankingSystemFactory.getInstance();
        final BankingSystem bankingSystem = bankingSystemFactory.createBankingSystem();
        bankingSystem.start();
    }
}
package banking.service;

public interface BankingMenuService {
    String getCommandTypeFromUser();

    void traverseSubMenuOfCurrentOption();

    void revertToPreviousMenu();
}

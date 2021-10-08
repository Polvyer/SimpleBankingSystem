package banking.algorithm;

@FunctionalInterface
public interface LuhnAlgorithm {
    boolean validateCreditCardNumber(final String cardNumber);
}

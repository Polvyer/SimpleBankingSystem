package banking.algorithm.impl;

import banking.algorithm.LuhnAlgorithm;

import java.util.Arrays;
import java.util.stream.IntStream;

public class LuhnAlgorithmImpl implements LuhnAlgorithm {

    @Override
    public boolean validateCreditCardNumber(final String cardNumber) {
        if (cardNumber.length() != 16) {
            return false;
        }
        final int[] digits = convertStringToIntegerArray(cardNumber);
        final int lastDigitIndex = digits.length - 1;
        final int lastDigit = digits[lastDigitIndex];
        int sumOfAllNumbers = IntStream.range(0, lastDigitIndex)
                .peek(index -> multiplyOddDigitsByTwo(digits, index))
                .peek(index -> subtractNineToNumbersOverNine(digits, index))
                .map(i -> digits[i])
                .sum()
                + lastDigit;
        return sumIsDivisibleByTen(sumOfAllNumbers);
    }

    private int[] convertStringToIntegerArray(final String string) {
        return Arrays.asList(string.split(""))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private void multiplyOddDigitsByTwo(final int[] digits, final int index) {
        final int adjustedIndex = index + 1;
        if (indexIsEven(adjustedIndex)) {
            return;
        }
        multiplyDigitInArrayByTwo(digits, index);
    }

    private boolean indexIsEven(final int index) {
        return index % 2 == 0;
    }

    private void multiplyDigitInArrayByTwo(final int[] digits, final int index) {
        digits[index] *= 2;
    }

    private void subtractNineToNumbersOverNine(final int[] digits, final int index) {
        if (numberIsNineOrBelow(digits[index])) {
            return;
        }
        subtractNineFromNumberInArray(digits, index);
    }

    private boolean numberIsNineOrBelow(final int number) {
        return number <= 9;
    }

    private void subtractNineFromNumberInArray(final int[] digits, final int index) {
        digits[index] -= 9;
    }

    private boolean sumIsDivisibleByTen(final int sum) {
        return (sum % 10) == 0;
    }
}

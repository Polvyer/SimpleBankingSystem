package banking.dto;

import java.util.List;
import java.util.stream.IntStream;

public final class Menu {

    private final List<MenuOption> options;

    private Menu(final MenuOption... options) {
        this.options = List.of(options);
    }

    public static Menu of(final MenuOption... options) {
        return new Menu(options);
    }

    public MenuOption getMenuOptionFromUserChoice(final int userChoice) {
        return options.get(userChoice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(1, options.size())
                .forEach(i -> sb.append(i)
                        .append(". ")
                        .append(options.get(i))
                        .append(System.lineSeparator())
                );
        sb.append("0. ").append(options.get(0));
        return sb.toString();
    }
}

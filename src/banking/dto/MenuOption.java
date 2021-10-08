package banking.dto;

import java.util.Optional;

public final class MenuOption {

    private final String text;
    private Optional<Menu> subMenu;

    public MenuOption(final String text) {
        this(text, null);
    }

    public MenuOption(final String text,
                      final Menu subMenu) {
        this.text = text;
        this.subMenu = Optional.ofNullable(subMenu);
    }

    public Optional<Menu> getSubMenu() {
        return subMenu;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }
}

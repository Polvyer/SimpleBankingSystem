package banking.command;

public final class CommandController {

    private Command command;

    public void setCommand(final Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}

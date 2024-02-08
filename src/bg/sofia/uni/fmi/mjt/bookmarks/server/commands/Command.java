package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

public abstract class Command {
    protected String[] params;

    //public abstract void execute();

    public static Command newCommand(String input) {
        for (CommandPattern comPattern : CommandPattern.values()) {
            if (comPattern.getPattern().matcher(input).matches()) {
                return comPattern.getConstructor().apply(input);
            }
        }
        return null;
    }
}

package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

public abstract class Command {
    protected String[] params;

    //public abstract void execute();

    public static Command newCommand(String input) {
        for (CommandRegEx regEx : CommandRegEx.values()) {
            if (regEx.getPattern().matcher(input).matches()) {
                return regEx.getConstructor().apply(input);
            }
        }
        return null;
    }
}

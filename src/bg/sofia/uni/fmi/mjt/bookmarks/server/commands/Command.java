package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;

public abstract class Command {
    protected String[] params;
    public abstract int execute(Storage storage, int userID) throws IOException;

    public static Command newCommand(String input) {
        for (CommandPattern comPattern : CommandPattern.values()) {
            if (comPattern.getPattern().matcher(input).matches()) {
                return comPattern.getConstructor().apply(input);
            }
        }
        return null;
    }
}

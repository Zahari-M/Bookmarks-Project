package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginCommand extends Command {

    private static final int USERNAME_NUMBER = 1;
    private static final int PASSWORD_NUMBER = 2;
    private static final int ARGC = 3;

    LoginCommand(String input) {
        Matcher matcher = CommandPattern.LOGIN.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[USERNAME_NUMBER] = matcher.group(USERNAME_NUMBER);
        this.params[PASSWORD_NUMBER] = matcher.group(PASSWORD_NUMBER);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        this.userID = storage.getUser(params[USERNAME_NUMBER], params[PASSWORD_NUMBER]);
    }
}

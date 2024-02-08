package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import java.util.regex.Matcher;

public class RegisterCommand extends Command {

    private static final int USERNAME_NUMBER = 1;
    private static final int PASSWORD_NUMBER = 2;
    private static final int ARGC = 3;

    RegisterCommand(String input) {
        Matcher matcher = CommandRegEx.REGISTER.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[USERNAME_NUMBER] = matcher.group(USERNAME_NUMBER);
        this.params[PASSWORD_NUMBER] = matcher.group(PASSWORD_NUMBER);
    }
}

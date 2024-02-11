package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import java.util.function.Function;
import java.util.regex.Pattern;

enum CommandPattern {
    REGISTER("^register +(\\S+) +(\\S+) *$", RegisterCommand::new),
    LOGIN("^login +(\\S+) +(\\S+) *$", LoginCommand::new),
    NEW_GROUP("^new-group +(\\S+) *$", NewGroupCommand::new),
    ADD_TO("^add-to +(\\S+) +(\\S+) *(--shorten)? *$", AddToCommand::new),
    REMOVE_FROM("^remove-from +(\\S+) +(\\S+) *$", RemoveFromCommand::new),
    LIST("^list *$", (str) -> new ListCommand()),
    LIST_GROUP("^list +--group-name +(\\S+) *$", ListGroupNameCommand::new),
    SEARCH_TAGS("^search +--tags(?: +(?:\\S+))+ *$", SearchTagsCommand::new),
    SEARCH_TITLE("^search +--title +(\\S+) *$", SearchTitleCommand::new),
    CLEANUP("^cleanup *$", (str) -> new CleanupCommand());

    private final Pattern pattern;
    private final Function<String, Command> constructor;

    CommandPattern(String string, Function<String, Command> constructor) {
        this.pattern = Pattern.compile(string);
        this.constructor = constructor;
    }

    Pattern getPattern() {
        return pattern;
    }

    Function<String, Command> getConstructor() {
        return constructor;
    }
}

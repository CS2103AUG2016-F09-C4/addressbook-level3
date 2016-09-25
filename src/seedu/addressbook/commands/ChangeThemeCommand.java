package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.ui.Gui;

public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    
    public static String MESSAGE_USAGE = String.format(COMMAND_WORD + ":\n" 
            + "Changes theme of the application.\n\t"
            + "Parameters: NEW_THEME\n\t"
            + "Themes available: %1$s\n\t"
            + "Example: " + COMMAND_WORD + " Light", AddressBook.allThemesString());
    
    public static final String MESSAGE_SUCCESS = "Theme changed successfully";
    public static final String MESSAGE_FAILED_THEME_DOES_NOT_EXIST = "Theme %1$s does not exist.\n";

    private final String newThemeName;
    
    public ChangeThemeCommand(String newThemeName) {
        this.newThemeName = newThemeName;
    }
    
    @Override
    public CommandResult execute() {
        if (!addressBook.containsTheme(newThemeName)) {
            return new CommandResult(String.format(MESSAGE_FAILED_THEME_DOES_NOT_EXIST, newThemeName));
        }
        Gui.changeTheme(newThemeName);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
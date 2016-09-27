package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;

/**
 * Show persons with the tags user keyed in.
 * @author xuchen
 *
 */
public class ShowTagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Show non-private information of persons containing specific tags"
            + "Example: tag students class2019";
    
    private final Set<String> tags;
    private final Set<String> tagsNotFound;
    
    public ShowTagCommand(String tags) {
        this.tags = parseStringToTagSet(tags);
        this.tagsNotFound = new HashSet<>();
    }
    
    private Set<String> parseStringToTagSet(String tags) {
        return new HashSet<>(Arrays.asList(tags.trim().split(" ")));
    }

    @Override
    /**
     * Get result of the command where only non-private information of the person's data is shown.
     */
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithTags(tags);
        return new CommandResult(getMessageForShowTagsSummary(personsFound, tagsNotFound), personsFound);
    }


    private String getMessageForShowTagsSummary(List<ReadOnlyPerson> personsFound, Set<String> tagsNotFound) {
        return String.format(Messages.MESSAGE_TAG_NOT_FOUND_OVERVIEW, tagsNotFound.size())
                + tagNotValidToString(tagsNotFound)
                + getMessageForPersonListShownSummary(personsFound);
    }


    private String tagNotValidToString(Set<String> tagsNotFound) {
        StringBuilder sb = new StringBuilder();
        for(String tag: tagsNotFound) {
            sb.append("[")
            .append(tag)
            .append("]");
        }
        sb.append("\n");
        return sb.toString();
    }


    private List<ReadOnlyPerson> getPersonsWithTags(Set<String> tags) {
        List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        Set<Tag> inputTagSet = new HashSet<>();
        
        for(String tag : tags) {
            try{
                inputTagSet.add(new Tag(tag));
            } catch (IllegalValueException e) {
                tagsNotFound.add(tag);
            }
        }
        
        for(ReadOnlyPerson person: addressBook.getAllPersons()) {
            final Set<Tag> tagSet = new HashSet<>(person.getTags().toSet());
            if(!Collections.disjoint(tagSet, inputTagSet)) {
                matchedPersons.add(person);
            }
        }
        
        return matchedPersons;
    }

}

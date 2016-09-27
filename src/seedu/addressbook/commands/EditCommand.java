package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Edits a person in the address book. 
 * Able to edit all contact fields but not tags.
 */

public class EditCommand extends Command {

    private static final String DEFAULT_PERSON_ADDRESS = "5 Commonwealth Avenue";
    private static final String DEFAULT_PERSON_EMAIL = "cs2103@nus.com";
    private static final String DEFAULT_PHONE_STRING = "123456789";
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ":\n" + "Edits a person in the address book identified by the index number in the last person listing. "
            + "Can edit contact details but not tags. Edited contacts are not private\n\t"
            + "Parameters: PERSON INDEX p/PHONE e/EMAIL a/ADDRESS \n\t"
            + "Example: " + COMMAND_WORD
            + " 1 p/98765432 e/johnd@gmail.com ";

    public static final String MESSAGE_SUCCESS = "Person edited: %1$s";    
    
    private Phone newPhone;
    private Email newEmail;
    private Address newAddress;
    private boolean isPhoneToBeEdit;
    private boolean isEmailToBeEdit;
    private boolean isAddressToBeEdit;
    
    
    /**
     * Convenience constructor using raw values.
     * Only fields to be edited will have values parsed in.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand( Integer index, 
                        boolean isPhoneToBeEdited, String phone, 
                        boolean isEmailToBeEdited, String email, 
                        boolean isAddressToBeEdited, String address) throws IllegalValueException {
        
        this.setTargetIndex(index);
        this.isPhoneToBeEdit = isPhoneToBeEdited;
        this.isEmailToBeEdit = isEmailToBeEdited;
        this.isAddressToBeEdit = isAddressToBeEdited;

        newPhone = null;
        newEmail = null;
        newAddress = null;
        
        if (isPhoneToBeEdited) {
            newPhone = new Phone(phone, false);
        } 
        if (isEmailToBeEdited) {
            newEmail = new Email(email, false);
        } 
        if (isAddressToBeEdited) {
            newAddress = new Address(address, false);
        }          
    }
    
    /**
     * Edits the contact details of the target person
     * @return Person with edited fields completed.
     */    
    private Person editPerson(ReadOnlyPerson target) {
        if (!isPhoneToBeEdit) {
            newPhone = target.getPhone();
        } 
        if (!isEmailToBeEdit) {
            newEmail = target.getEmail();
        } 
        if (!isAddressToBeEdit) {
            newAddress = target.getAddress();
        }
        return new Person (target.getName(),this.newPhone,this.newEmail,this.newAddress,target.getTags());
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            Person editPerson = editPerson(target);
            addressBook.editPerson(editPerson, this.getTargetIndex());
            return new CommandResult(String.format(MESSAGE_SUCCESS, editPerson));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } 
    }

}

package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ":\n" + "Edits a person in the address book identified by the index number in the last person listing. "
            + "Can edit contact details but not tags. Edited contacts are not private\n\t"
            + "Parameters: PERSON INDEX p/PHONE e/EMAIL a/ADDRESS \n\t"
            + "Example: " + COMMAND_WORD
            + " 1 p/98765432 e/johnd@gmail.com ";

    public static final String MESSAGE_SUCCESS = "Person edited: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    
    private String toEdit = "blad";
    private Phone newPhone = new Phone("1234", false);
    private Email newEmail = new Email("km@live", false);
    private Address newAddress = new Address("22 Daisy", false);
    private boolean isPhoneToBeEdit = false;
    private boolean isEmailToBeEdit = false;
    private boolean isAddressToBeEdit = false;
    
    public EditCommand( Integer index, 
                        boolean isPhoneToBeEdited, String phone, 
                        boolean isEmailToBeEdited, String email, 
                        boolean isAddressToBeEdited, String address) throws IllegalValueException {
        
        this.setTargetIndex(index);

        if (isPhoneToBeEdited) {
            newPhone = new Phone(phone, false);
            this.isPhoneToBeEdit = isPhoneToBeEdited;
        } 
        if (isEmailToBeEdited) {
            newEmail = new Email(email, false);
            this.isEmailToBeEdit = isEmailToBeEdited;
        } 
        if (isAddressToBeEdited) {
            newAddress = new Address(address, false);
            this.isAddressToBeEdit = isAddressToBeEdited;
        }       
    }
    
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
            addressBook.removePerson(target);
            addressBook.addPerson(editPerson);
            return new CommandResult(String.format(MESSAGE_SUCCESS, editPerson));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }

}

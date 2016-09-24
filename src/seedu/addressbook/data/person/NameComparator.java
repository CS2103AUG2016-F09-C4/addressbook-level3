package seedu.addressbook.data.person;
 //as;
import java.util.Comparator;

public class NameComparator implements Comparator<Person>{

    @Override
    public int compare(Person a, Person b) {
        String a_name = a.getName().toString();
        String b_name = b.getName().toString();
        
        return a_name.compareToIgnoreCase(b_name);
    }

}

package a3.ybond.contactsapp;

import java.util.Comparator;

// Comparator<type> is a built in java class that can take any type
// public class ContactComparator is just a customized type we made to sort contact lists
// by last name
// implements allows us to use only one of the fuctions that the built-in class contains
public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact t1) {
        return contact.getLastName().compareToIgnoreCase(t1.getLastName());
    }
}

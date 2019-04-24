package com.pmt.health.objects.user;

/**
 * An object to store the user's two emergency contacts
 * used in the basics form
 */
public class Contacts {
    private User firstContact;
    private User secondContact;

    /**
     * Return the first contact
     *
     * @return the user object of the first contact
     */
    public User getFirstContact() {
        return firstContact;
    }

    /**
     * Set the first contact
     *
     * @param user user object to set as the first contact
     */
    public void setFirstContact(User user) {
        this.firstContact = user;
    }

    /**
     * Return the second contact
     *
     * @return the user object of the second contact
     */
    public User getSecondContact() {
        return secondContact;
    }

    /**
     * Set the second contact
     *
     * @param user user object to set as the second contact
     */
    public void setSecondContact(User user) {
        this.secondContact = user;
    }

    /**
     * Return the number of contacts set
     *
     * @return number of contacts set
     */
    public int numberOfContacts() {
        int contacts = 0;
        if (firstContact != null) {
            contacts++;
        }
        if (secondContact != null) {
            contacts++;
        }
        return contacts;
    }

    /**
     * Return whether the contacts object is empty,
     * when no contacts are set
     *
     * @return boolean of whether the contacts object is empty
     */
    public boolean isEmpty() {
        return firstContact == null && secondContact == null;
    }
}

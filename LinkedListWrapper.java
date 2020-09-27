import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class is a wrapper for a linkedLIst object to enable declaring it in an array
 * @author guyna25
 */

public class LinkedListWrapper {

    private LinkedList<String> linkedList;

    public LinkedListWrapper() {
        linkedList = new LinkedList<>();
    }

    /**
     * Adds a string to the list
     * @param string the string to be added
     * @return true if string was added succesfully, false otherwise
     */

    public boolean add(String string) {
        return linkedList.add(string);
    }

    /**
     * Removes a string to the list
     * @param string the string to be Removed
     * @return true if string was removed succesfully, false otherwise
     */

    public boolean remove(String string) {
        return linkedList.remove(string);
    }

    /**
     * Check if a string is inside the linked list
     * @param string the string to be looked for
     * @return true if string is in the list, false otherwise
     */

    public boolean contains(String string) {
        return linkedList.contains(string);
    }

    /**
     * @return An iterator of the strings held in the linked list
     */

    public Iterator<String> getIterator() {
        return linkedList.iterator();
    }
}

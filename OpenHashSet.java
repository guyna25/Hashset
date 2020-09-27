import java.util.Iterator;

/**
 * This class represents an open hashset
 * @author guyna25
 */

public class OpenHashSet extends SimpleHashSet{

    /**
     * An array that contains linked lists which contain all elements held in this hash set
     */

    private LinkedListWrapper[] hashSet;

    /**
     * Default constructor for this class
     */

    public OpenHashSet() {
        hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
        setCapacityMinusOne(INITIAL_CAPACITY);
    }

    /**
     * Constructor with given load factors
     * @param upperLoadFactor upper load factor for this hashset
     * @param lowerLoadFactor lower load facotr for this hashset
     */

    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        setCapacityMinusOne(INITIAL_CAPACITY);
        hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data the data to be added
     */

    public OpenHashSet(String[] data) {
        setCapacityMinusOne(INITIAL_CAPACITY);
        hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
        if (data.length/ (float) capacity() > getLowerLoadFactor()) { //add method resides the table which
            // is an unnecessary operation if the amount of elements entered would surpass the lower load
            // factor in relation to the initial capacity
            for (String string : data) {
                if (string != null) {
                    hashElement(string);
                }
            }
        } else {
            for (java.lang.String string : data) {
                if (string != null) {
                    add(string);
                }
            }
        }
    }

    public int capacity() {
        return hashSet.length;
    }

    /**
     * This method resizes the table in accordance of load factor and reashes all values into the new table
     */

    protected void rehash(Boolean enlarge) {
        if (hashSet.length == 1) {
            return;
        }
        LinkedListWrapper[] oldHashSet = hashSet;
        if (enlarge) {
            setCapacityMinusOne(capacity() * RESIZE_BASE); //note this is similar but sufficently
            // different from rehashing in closedhashset so I cannot turn this into a generic rehash function
            hashSet = new LinkedListWrapper[capacity() * RESIZE_BASE];
        } else {
            setCapacityMinusOne(capacity() / RESIZE_BASE);
            hashSet = new LinkedListWrapper[capacity() / RESIZE_BASE];
        }
        resetStoredItemsQuantity();
        for (LinkedListWrapper linkedList: oldHashSet) {
            if (linkedList == null) {
                continue;
            }
            Iterator<String> linkedListIterator = linkedList.getIterator();
            while (linkedListIterator.hasNext()) {
                hashElement(linkedListIterator.next());
            }
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */

    public boolean add(String newValue) {
        if (newValue == null || contains(newValue)) {
            return false;
        }
        if (overUpperBound()) {
            rehash(true);
        }
        hashElement(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */

    public boolean contains(String searchVal) {
        int searchIndex = clamp(searchVal.hashCode());
        return this.hashSet[searchIndex] != null && this.hashSet[searchIndex].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */

    public boolean delete(String toDelete) {
        if (toDelete == null || hashSet[clamp(toDelete.hashCode())] == null ||
                !hashSet[clamp(toDelete.hashCode())].remove(toDelete)) {
            return false;
        }
        decreaseStoredElements();
        if (underLowerBound()) {
            rehash(false);
        }
        return true;
    }

    private void hashElement(String element) {
        int elementHashCode = clamp(element.hashCode());
        if (hashSet[elementHashCode] == null) {
            hashSet[elementHashCode] = new LinkedListWrapper();
        }
        hashSet[elementHashCode].add(element);
        increaseStoredElments();
    }
}

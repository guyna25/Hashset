import java.util.HashSet;

/**
 * This class represents a closed hashset
 *
 * @author guyna25
 */

public class ClosedHashSet extends SimpleHashSet {

    /**
     * Constant used to mark hashset indexes which don't hold a value but
     */

    private final String EMPTY_BUT_DELETED = new String();

    /**
     * The constant used in the hashing formula given in the relevant segment in this exercise
     */

    private final double HASHING_CONSTANT = 0.5;

    /**
     * An array that contains linked lists which contain all elements held in this hash set
     */

    private String[] hashSet;

    /**
     * Default constructor for this class
     */

    public ClosedHashSet() {
        hashSet = new String[INITIAL_CAPACITY];
        setCapacityMinusOne(INITIAL_CAPACITY);
    }

    /**
     * Constructor with given load factors
     *
     * @param upperLoadFactor upper load factor for this hashset
     * @param lowerLoadFactor lower load facotr for this hashset
     */

    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        setCapacityMinusOne(INITIAL_CAPACITY);
        hashSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     *
     * @param data the data to be added
     */

    public ClosedHashSet(String[] data) {
        setCapacityMinusOne(INITIAL_CAPACITY);
        hashSet = new String[INITIAL_CAPACITY];
        for (java.lang.String string : data) {
            if (string != null) {
                add(string);
            }
        }
    }

    public int capacity() {
        return hashSet.length;
    }

    /**
     * This method resizes the table in accordance of load factor and reashes all values into the new table
     *
     * @param enlarge true if table should be enlarged and false if table should be downsized
     */

    protected void rehash(Boolean enlarge) {
        if (hashSet.length == 1) {
            return;
        }
        String[] oldHashSet = hashSet;
        if (enlarge) {
            setCapacityMinusOne(hashSet.length * RESIZE_BASE);
            hashSet = new String[hashSet.length * RESIZE_BASE];
        } else if (underLowerBound()) {
            setCapacityMinusOne(hashSet.length / RESIZE_BASE);
            hashSet = new String[hashSet.length / RESIZE_BASE];
        }
        for (String cell : oldHashSet) {
            if (cell != null && !cell.equals(EMPTY_BUT_DELETED)) {
                hashSet[searchForStringIndex(cell, true)] = cell;
            }
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */

    public boolean add(String newValue) {
        int newValueHashCode = searchForStringIndex(newValue, true);
        if (newValue == null || searchForStringIndex(newValue, false) != -1) {
            return false;
        }
        if (overUpperBound()) {
            rehash(true);
        }
        hashSet[newValueHashCode] = newValue;
        increaseStoredElments();
        return true;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */

    public boolean contains(String searchVal) {
        if (searchVal == null) {
            return false;
        }
        return searchForStringIndex(searchVal, false) != -1;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */

    public boolean delete(String toDelete) {
        int toDeleteHashCode = searchForStringIndex(toDelete, false);
        if (toDelete == null || toDeleteHashCode == -1) {
            return false;
        }
        hashSet[toDeleteHashCode] = EMPTY_BUT_DELETED;
        decreaseStoredElements();
        if (underLowerBound()) {
            rehash(false);
        }
        return true;
    }

    /**
     * THis is a helper method that can either search for a string, or find an empty index for a string
     *
     * @param searchString      the string to be used for searching
     * @param lookForEmptySpace true if an empty space should be looked for, false other wise
     * @return relevant string index, -1 otherwise
     */

    private int searchForStringIndex(String searchString, Boolean lookForEmptySpace) {
        if (searchString == null) {
            return -1;
        }
        HashSet<String> indexSet = new HashSet<>();
        int attempCounter = 0;
        int searchForHashCode = clamp((int) (searchString.hashCode() + HASHING_CONSTANT * (attempCounter
                * attempCounter + attempCounter)));
        while (indexSet.size() < capacity()) {
            if (lookForEmptySpace) {
                if (hashSet[searchForHashCode] == null ||
                        EMPTY_BUT_DELETED.equals(hashSet[searchForHashCode])) {
                    return searchForHashCode;
                }
            } else {
                if (searchString.equals(hashSet[searchForHashCode])) {
                    return searchForHashCode;
                }
            }
            indexSet.add(String.valueOf(searchForHashCode));
            attempCounter++;
            searchForHashCode = clamp((int) (searchString.hashCode() + HASHING_CONSTANT * (attempCounter
                    * attempCounter + attempCounter)));
        }
        return -1;
    }
}

/**
 * This class represents the abstract construct of a hashset
 * @author guyna25
 */

public abstract class SimpleHashSet implements SimpleSet{

    /**
     * The default upper load factor of a hashtable
     */

    private final float DEFAULT_UPPER_LOAD_FACTOR = 0.75f;

    /**
     * The default lower load facot rof a hashtable
     */

    private final float DEFAULT_LOWER_LOAD_FACTOR = 0.25f;


    /**
     * The default initial capacityMinusOne for a hash table
     */

    protected final int INITIAL_CAPACITY = 16;

    /**
     * When the table is resized this is the base for increasing/decreasing its size
     */

    protected final int RESIZE_BASE = 2;

    /**
     * The upper load factor of this hashtabl
     */

    private float topLoadFactor;

    /**
     * The lower load factor of this hashtabl
     */

    private float bottomLoadFactor;

    /**
     * The hashset capacityMinusOne
     */

    protected int capacityMinusOne;

    /**
     * The quantity of elements stored in the hashset
     */

    private int storedElementsQuantity = 0;

    /**
     * Default Constructor for this class
     */

    public SimpleHashSet() {
        topLoadFactor = DEFAULT_UPPER_LOAD_FACTOR;
        bottomLoadFactor = DEFAULT_LOWER_LOAD_FACTOR;
    }

    /**
     * Constructor for this class that accepts parameters
     * @param upperLoadFactor upper load factor of the hash table
     * @param lowerLoadFactor lower load factor of the hash table
     */

    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        topLoadFactor = upperLoadFactor;
        bottomLoadFactor = lowerLoadFactor;
    }

    /**
     * @return The current capacityMinusOne (number of cells) of the table.
     */

    public abstract int capacity();

    /**
     * This method clamps an index so that it fits the hashset capacityMinusOne
     * @param index the index of the element
     * @return the index fitting the size of the hash set
     */

    protected int clamp(int index) {
        return index & capacityMinusOne;
    }

    /**
     * Method to reas the values into the table
     */

    protected abstract void rehash(Boolean enlarge); ;

    /**
     * @return the lower load factor of the hash set
     */

    protected float getLowerLoadFactor() {
        return bottomLoadFactor;
    }

    /**
     * @return The upper load factor of this method
     */

    protected float getUpperLoadFactor() {
        return topLoadFactor;
    }

    /**
     * @return The amount of elements stored in the hash set
     */

    public int size() {
        return storedElementsQuantity;
    }

    /**
     * Setter method for capacityMinusOne
     * @param newCapacity the cpacity to be set (minus one)
     */

    protected void setCapacityMinusOne(int newCapacity) {
        capacityMinusOne = newCapacity - 1;
    }

    /**
     * Setter method to increase the number of stored elements after an element is added
     */

    protected void increaseStoredElments() {
        storedElementsQuantity++;
    }

    /**
     * Setter method to decrease the quantity of item stored after an item is removed
     */

    protected void decreaseStoredElements() {
        storedElementsQuantity--;
    }

    /**
     * @return true if hash table load factor is over upper bound, false otherwise
     */

    protected boolean overUpperBound() {
        return (size()+1) / (float)(capacityMinusOne +1) > topLoadFactor;
    }

    /**
     * @return true if hash table load factor is under lower bound, false otherwise
     */

    protected boolean underLowerBound() {
        return (storedElementsQuantity / (float)(capacityMinusOne +1)) < bottomLoadFactor;
    }


    /**
     *Resets the amount of stored items quantity
     */

    protected void resetStoredItemsQuantity() {
        storedElementsQuantity = 0;
    }
}

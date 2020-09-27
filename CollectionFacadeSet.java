/**
 * This class is a facade that can wrap a collection and delegate methods to it
 * @author guyna25
 */

public class CollectionFacadeSet implements SimpleSet {

    protected java.util.Collection<java.lang.String> collectionSet;

    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        collectionSet = collection;
        for (String element: collection) {
            if (contains(element) || element == null) {
                continue;
            }
            add(element);
        }
    }

    public boolean add(String newValue) {
        return !collectionSet.contains(newValue) && collectionSet.add(newValue);
    }

    public boolean contains(String searchVal) {
        return collectionSet.contains(searchVal);
    }

    public boolean delete(String toDelete) {
        return collectionSet.contains(toDelete) && collectionSet.remove(toDelete);
    }

    public int size() {
        return collectionSet.size();
    }
}

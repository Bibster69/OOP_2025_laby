import java.util.Collection;
import java.util.Comparator;

public class CollectionSizeComparator implements Comparator<Collection<?>> {
    @Override
    public int compare(Collection<?> c1, Collection<?> c2) {
        return Integer.compare(c1.size(), c2.size());
    }
}

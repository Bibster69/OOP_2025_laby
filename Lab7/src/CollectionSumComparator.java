import java.util.Collection;
import java.util.Comparator;

public class CollectionSumComparator implements Comparator<Collection<? extends Number>> {
    @Override
    public int compare(Collection<? extends Number> c1, Collection<? extends Number> c2) {
        double sum1 = 0;
        for (Number n : c1) {
            sum1 += n.doubleValue();
        }

        double sum2 = 0;
        for (Number n : c2) {
            sum2 += n.doubleValue();
        }

        return Double.compare(sum1, sum2);
    }
}

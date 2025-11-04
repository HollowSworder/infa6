import java.util.ArrayList;
import java.util.List;

public class Cache<T> {

    private List<T> items; 
    private int maxCountElements; 

    public Cache(int maxCountElements) {
        this.maxCountElements = maxCountElements;
        this.items = new ArrayList<T>();
    }

    public void add(T item) {
        if (items.size() >= maxCountElements) {
            items.remove(0); 
        }
        items.add(item);
    }
}
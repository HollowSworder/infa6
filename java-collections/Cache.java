import java.util.ArrayList;
import java.util.List;

public class Cache<T> {

    private List<T> items; 
    private int maxCountElements; 

    public Cache(int maxCountElements) {
        this.maxCountElements = maxCountElements;
        this.items = new ArrayList<T>();
    }
}
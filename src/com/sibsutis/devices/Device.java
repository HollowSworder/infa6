package src.com.sibsutis.devices;
import java.util.Objects;
import src.com.sibsutis.Printable;

public abstract class Device implements Printable {
    private int id;       
    private int price;    
    private String ip;    

    public Device(int id, int price, String ip) {
        this.id = id;
        this.price = price;
        this.ip = ip;
    }
    public int getId() {
        return id;
    }
    public int getPrice() {
        return price;
    }
    public String getIp() {
        return ip;
    }

    @Override
    public String print() {
            return "Device type: " + getDeviceType() + ", ID: " + id + ", Price: " + price + ", IP: " + (ip != null ? ip : "null");
    }
    public abstract String getDeviceType();
    @Override
    public abstract boolean equals(Object obj);
    @Override
    public abstract int hashCode();
}

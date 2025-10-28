package src.com.sibsutis.devices;
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
    public abstract String getDeviceType();
    @Override
    public String print() {
            return "Device type: " + getDeviceType() + ", ID: " + id + ", Price: " + price + ", IP: " + (ip != null ? ip : "null");
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Device)) return false;
        Device other = (Device) obj;
        return id == other.id && price == other.price &&
                (ip == null ? other.ip == null : ip.equals(other.ip));
    }
    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + Integer.hashCode(price);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }
}

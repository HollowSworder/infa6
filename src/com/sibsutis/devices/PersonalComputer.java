package src.com.sibsutis.devices;

public class PersonalComputer extends Device {

    public PersonalComputer(int id, int price) {
        this(id, price, null);
    }

    public PersonalComputer(int id, int price, String ip) {
        super(id, price, ip);
    }

    @Override
    public String getDeviceType() {
        return "PersonalComputer";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + 1;
    }
}

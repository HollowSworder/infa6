public class car {

    private int id;
    private String brand;
    private String model;
    private int year;
    private String color;
    private int price;
    private String registrationNumber;

    public function __construct() {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
        this.registrationNumber = registrationNumber;
    }
    public int getId() { 
        return id;
    }
    public void setId(int id) { 
        this.id = id; 
    }
    public String getBrand() { 
        return brand; 
    }
    public void setBrand(String brand) { 
        this.brand = brand;
    }
    public String getModel() { 
        return model; 
    }
    public void setModel(String model) { 
        this.model = model;
    }
    public int getYear() { 
        return year; 
    }
    public void setYear(int year) { 
        this.year = year; 
    }
    public String getColor() {
         return color; 
    }
    public void setColor(String color) { 
        this.color = color; 
    }
    public double getPrice() { 
        return price; 
    }
    public void setPrice(double price) { 
        this.price = price; 
    }
    public String getRegistrationNumber() { 
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) { 
        this.registrationNumber = registrationNumber; 
    }
}

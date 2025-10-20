import java.time.Year;

public class Main {
    public static void main(String[] args) {

        Car[] cars = {
            new Car(1, "Toyota", "Camry", 2020, "Black", 25000, "AB123CD"),
            new Car(2, "Honda", "Civic", 2019, "White", 22000, "EF456GH"),
            new Car(3, "Toyota", "Corolla", 2021, "Red", 20000, "IJ789KL"),
            new Car(4, "Toyota", "RAV4", 2018, "Blue", 28000, "MN012OP")
        };

        Car[] toyotaCars = getCarByBrend(cars, "Toyota");
        System.out.println("Найдено Toyota: " + toyotaCars.length);
        for (Car car : toyotaCars) {
            System.out.println(car.getBrand() + " " + car.getModel() + " " + car.getYear());
        }

        Car[] oldToyotas = getCarByBrendAndYearOperational(cars, "Toyota", 3);
        System.out.println("\nToyota старше 3 лет: " + oldToyotas.length);
        for (Car car : oldToyotas) {
            System.out.println(car.getBrand() + " " + car.getModel() + " " + car.getYear() + 
                             " (возраст: " + (Year.now().getValue() - car.getYear()) + " лет)");
        }
    }

    public static Car[] getCarByBrend(Car[] cars, String brend) {
        int count = 0;
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brend)) {
                count++;
            }
        }

        Car[] result = new Car[count];
        int index = 0;
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brend)) {
                result[index++] = car;
            }
        }
        return result;
    }

    public static Car[] getCarByBrendAndYearOperational(Car[] cars, String brend, int years) {
        int currentYear = Year.now().getValue();
        int count = 0;
        
        for (Car car : cars) {
            int carAge = currentYear - car.getYear();
            if (car.getBrand().equalsIgnoreCase(brend) && carAge > years) {
                count++;
            }
        }

        Car[] result = new Car[count];
        int index = 0;
        for (Car car : cars) {
            int carAge = currentYear - car.getYear();
            if (car.getBrand().equalsIgnoreCase(brend) && carAge > years) {
                result[index++] = car;
            }
        }
        return result;
    }
}
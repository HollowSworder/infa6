public class Main {
    public static void main(String[] args) {

        Car[] cars = {
            new Car(1, "Toyota", "Camry", 2020, "Black", 25000, "AB123CD"),
            new Car(2, "Honda", "Civic", 2019, "White", 22000, "EF456GH"),
            new Car(3, "Toyoqweta", "Corolla", 2021, "Red", 20000, "IJ789KL")
        };

        Car[] toyotaCars = getCarByBrend(cars, "Toyota");
        System.out.println("Найдено Toyota: " + toyotaCars.length);
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
}
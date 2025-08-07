import java.util.*;

abstract class Vehicle {
    private String registrationNumber;
    private boolean isAvailable;
    protected Map<String, Object> features;

    public Vehicle(String regNum, String fuel, Map<String, Object> features) {
        this.registrationNumber = regNum;
        this.isAvailable = true;
        this.features = features;
    }

    // getter and setterss
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public Map<String, Object> getFeatures() {
        return features;
    }

    public abstract String getVehicleType();

    public abstract double calculatePrice();
}

class Car extends Vehicle {
    public Car(String regNum, String fuel, Map<String, Object> features) {
        super(regNum, fuel, features);
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public double calculatePrice() {
        double basePrice = 80;
        if (features.containsKey("roof") && (Boolean) features.get("roof")) {
            basePrice += 10;
        }
        return basePrice;
    }
}

class Truck extends Vehicle {
    public Truck(String regNum, String fuel, Map<String, Object> features) {
        super(regNum, fuel, features);
    }

    @Override
    public String getVehicleType() {
        return "Truck";
    }

    @Override
    public double calculatePrice() {
        double basePrice = 175;
        if (features.containsKey("Cap")) {
            basePrice += ((int) features.get("Cap")) * 0.05;
        }
        return basePrice;
    }
}

class Motor extends Vehicle {
    public Motor(String regNum, String fuel, Map<String, Object> features) {
        super(regNum, fuel, features);
    }

    @Override
    public String getVehicleType() {
        return "Motor";
    }

    @Override
    public double calculatePrice() {
        return 50;
    }
}

public class VehicleOrderSystem {
    List<Vehicle> inventory;

    public VehicleOrderSystem() {
        inventory = new ArrayList<>();

        Map<String, Object> carFeatures = new HashMap<>();
        carFeatures.put("roof", true);
        carFeatures.put("TT", "Automatic");
        inventory.add(new Car("mo2", "Petrol", carFeatures));

        Map<String, Object> truckFeatures = new HashMap<>();
        truckFeatures.put("Cap", 1500);
        inventory.add(new Truck("mo", "Diesel", truckFeatures));
    }

    public boolean checkAvailability(String type, Map<String, Object> desiredFeatures) {
        for (Vehicle v : inventory) {
            if (v.getVehicleType().equals(type) && v.isAvailable()) {
                boolean matches = true;
                for (String key : desiredFeatures.keySet()) {
                    if (!v.getFeatures().containsKey(key)
                            || !v.getFeatures().get(key).equals(desiredFeatures.get(key))) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return true;
                }
            }
        }
        return false;
    }

    public Vehicle reserveVehicle(String type, Map<String, Object> desiredFeatures) {
        for (Vehicle v : inventory) {
            if (v.getVehicleType().equals(type) && v.isAvailable()) {
                boolean matches = true;
                for (String key : desiredFeatures.keySet()) {
                    if (!v.getFeatures().containsKey(key)
                            || !v.getFeatures().get(key).equals(desiredFeatures.get(key))) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    v.setAvailable(false);
                    return v;
                }
            }
        }
        return null;
    }

    public void placeOrder(String type, Map<String, Object> desiredFeatures) {
        if (checkAvailability(type, desiredFeatures)) {
            Vehicle v = reserveVehicle(type, desiredFeatures);
            double price = v.calculatePrice();
            System.out.println("Vehicle reserved: " + v.getRegistrationNumber());
            System.out.println("price: $" + price);
        } else {
            System.out.println("No criteria.");
        }
    }

    public static void main(String[] args) {
        VehicleOrderSystem system = new VehicleOrderSystem();
        Map<String, Object> carFeatures = new HashMap<>();
        carFeatures.put("roof", true);
        carFeatures.put("TT", "Automatic");
        system.placeOrder("Car", carFeatures);
        Map<String, Object> truckFeatures = new HashMap<>();
        truckFeatures.put("Cap", 1500);
        system.placeOrder("Truck", truckFeatures);
    }
}

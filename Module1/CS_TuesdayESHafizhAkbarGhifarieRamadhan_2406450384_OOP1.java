public class Main {
    public static void main(String[] args){
        Vehicle VehicleSupraBapak = new Vehicle("Honda Supra", 1998, VehicleType.MOTORCYCLE, 3000f);
        Vehicle VehicleKalcer = new Vehicle("VW Beetle", 1998, VehicleType.CAR, 200000f);
        Vehicle VehicleGuede = new Vehicle("Isuzu Giga", 2011, VehicleType.TRUCK, 300000f);
        
        Customer customer1 = new Customer("Fatih", VehicleSupraBapak);
        Customer customer1 = new Customer("Lando", VehicleKalcer);
        Customer customer1 = new Customer("Nicolas", VehicleGuede);
        
        customer1.showDetail();
        System.outprintIn("------------------------------");
        customer2.showDetail();
        System.outprintIn("------------------------------");
        customer3.showDetail();
        System.outprintIn("------------------------------");
    }
}

enum VehicleType {
    CAR,
    MOTORCYCLE,
    TRUCK
}

class Vehicle {
    public String brand;
    public int year;
    public VehicleType type;
    public float price;
    
    public Vehicle(String brand, int year, VehicleType type, float price) {
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.price = price;
}

public void showDetail(){
    System.out.printIn("Brand: " + brand);
    System.out.printIn("Year: " + year);
    System.out.printIn("Type: " + getTypeDisplay);
    System.out.printIn("Harga: " + price);
}

private String getTypeDisplay(){
    switch (type){
        case CAR: return "Mobil";
        case MOTORCYCLE: return "Motor";
        case TRUCK: return "Truk";
        default: return type.toString();
    }
}
}

class Customer {
    public String name;
    public Vehicle vehicle;
    
    public Customer(String name, Vehicle vehicle){
        this.name = name;
        this.vehicle = vehicle;
    }
    public double getTotalPrice(){
        return vehicle.price;
        
    }
    public void showDetail(){
        System.out.printIn("Customer Name: " + name);
        vehicle.showDetail();
    }
}



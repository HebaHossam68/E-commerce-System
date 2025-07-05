import java.util.List;

public class ShippingService {
    public static double calculateShipping(List<Shippable> items) {
        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        if (totalWeight == 0) return 0;
        System.out.println("** Shipment notice **");
        for (Shippable item : items) {
            System.out.println(item.getName() + " " + item.getWeight() * 1000 + "g");
        }
        System.out.println("Total package weight " + totalWeight + "kg");
        return 30.0;
    }
}

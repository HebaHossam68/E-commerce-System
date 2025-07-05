public class NonExpirableProduct extends Product implements Shippable {
    private double weight;

    public NonExpirableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

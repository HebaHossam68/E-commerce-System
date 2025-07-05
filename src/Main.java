import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Setup products
        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.of(2025, 8, 1), 0.2);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 3, LocalDate.of(2025, 9, 1), 0.7);
        Product tv = new NonExpirableProduct("TV", 300, 3, 5);
        Product scratchCard = new NonShippableProduct("Scratch Card", 50, 10);

        // Setup customer
        Customer customer = new Customer("Heba", 800);

        // Setup cart
        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(tv, 1);
            cart.add(scratchCard, 1);

            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) throw new IllegalStateException("Cart is empty");

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().isExpired()) {
                throw new IllegalStateException("Product expired: " + item.getProduct().getName());
            }
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new IllegalStateException("Out of stock: " + item.getProduct().getName());
            }
        }

        List<Shippable> toShip = cart.getShippableItems();
        double shippingCost = ShippingService.calculateShipping(toShip);

        double subtotal = cart.getSubtotal();
        double total = subtotal + shippingCost;

        if (customer.getBalance() < total) throw new IllegalStateException("Insufficient balance");

        // Deduct amounts
        customer.deduct(total);
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        // Print receipt
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingCost);
        System.out.println("Amount " + total);
        System.out.println("Remaining balance: " + customer.getBalance());
    }
}

import java.util.HashSet;
import java.util.Set;

public class Store {
    // Variable to store the products in the store
    private final Set<Product> inventory = new HashSet<>();

    /**
     * Adds new product to the store inventory
     * @param name of the product
     * @param quantity of the product
     */
    public void addProduct(String name, int quantity) {
        inventory.add(new Product(name, quantity));
    }

    /**
     * Sells product from the store.
     *
     * @param name of the product
     * @param quantity of the product to sell
     *
     * @return if sell was successfull or not
     */
    public synchronized boolean sellProduct(String name, int quantity) {
        Product product = inventory.stream().filter(i -> i.getName().equals(name)).findFirst().get();
        if (product != null) {
            return product.reduceQuantity(quantity);
        }
        return false;
    }

    /**
     * Prints the quantity for each product in the store.
     */
    public void showInventory() {
        System.out.println("\nStore Inventory:");
        inventory.forEach((product) -> System.out.println(product.getName() + ": " + product.getQuantity()));
        System.out.println();
    }
}
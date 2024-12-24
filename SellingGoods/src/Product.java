public class Product {
    // The name cannot be changed
    private final String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Reduces the quantity of the product.
     *
     * @param amount to reduce
     * @return if reduce was successfull or not
     */
    public synchronized boolean reduceQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
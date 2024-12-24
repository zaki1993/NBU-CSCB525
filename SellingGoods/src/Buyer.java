import java.util.Map;

public class Buyer implements Runnable {
    private final Store store;
    private final Cart cart;

    public Buyer(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
    }

    @Override
    public void run() {
        for (Map.Entry<String, Integer> entry : cart.getProducts().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            boolean success = store.sellProduct(productName, quantity);

            if (success) {
                System.out.println(Thread.currentThread().getName() + ": " + cart.getBuyerName() + ": Successfully bought " + quantity + " of " + productName);
            } else {
                System.out.println(Thread.currentThread().getName()+ ": " + cart.getBuyerName() + ": Failed to buy " + quantity + " of " + productName + " (not enough stock)");
            }
        }
    }
}

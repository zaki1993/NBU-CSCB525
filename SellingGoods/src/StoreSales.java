import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StoreSales {
    private static final String PRODUCT_MEAT = "Meat";
    private static final String PRODUCT_EGGS = "Eggs";
    private static final String PRODUCT_VITAMINS = "Vitamins";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfCarts = sc.nextInt();
        int numberOfThreads = sc.nextInt();

        // Create store and add products
        Store store = createStore(numberOfCarts);

        // Create shopping carts
        List<Cart> carts = createShoppingCarts(numberOfCarts);

        carts.forEach(System.out::println);

        // Show the store inventory before
        store.showInventory();

        // Run the tasks
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (Cart cart : carts) {
            executorService.execute(new Buyer(store, cart));
        }

        // Wait for threads to finish and close them
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Show the store inventory after
        store.showInventory();
    }

    /**
     * Creates store and adds products to it
     *
     * @return the store
     */
    private static Store createStore(int numberOfBuyers) {
        Store store = new Store();
        store.addProduct(PRODUCT_MEAT, ((int) (Math.random() * numberOfBuyers) + 1) * numberOfBuyers);
        store.addProduct(PRODUCT_EGGS, ((int) (Math.random() * numberOfBuyers) + 1) * numberOfBuyers);
        store.addProduct(PRODUCT_VITAMINS, ((int) (Math.random() * numberOfBuyers) + 1) * numberOfBuyers);

        return store;
    }

    /**
     * Creates the shopping carts for each of the buyers
     *
     * @return the shopping carts
     */
    private static List<Cart> createShoppingCarts(int numberOfCarts) {
        List<Cart> carts = new ArrayList<>();
        for (int i = 0; i < numberOfCarts; i++) {
            Map<String, Integer> products = new HashMap<>();
            products.put(PRODUCT_MEAT, (int) (Math.random() * numberOfCarts));
            products.put(PRODUCT_EGGS, (int) (Math.random() * numberOfCarts));
            products.put(PRODUCT_VITAMINS, (int) (Math.random() * numberOfCarts));
            Cart cart = new Cart("Buyer-" + i, products);

            carts.add(cart);
        }

        return carts;
    }
}

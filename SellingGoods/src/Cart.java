import java.util.Map;

public class Cart {
    private String buyerName;

    private Map<String, Integer> products;

    public Cart(String buyerName, Map<String, Integer> products) {
        this.buyerName = buyerName;
        this.products = products;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public String getBuyerName() {
        return buyerName;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "buyerName='" + buyerName + '\'' +
                ", products=" + products +
                '}';
    }
}

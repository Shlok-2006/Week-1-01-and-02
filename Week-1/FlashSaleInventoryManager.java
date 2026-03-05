import java.util.*;

public class FlashSaleInventoryManager {

    static HashMap<String, Integer> stock = new HashMap<>();
    static HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public static synchronized String checkStock(String productId) {
        int count = stock.getOrDefault(productId, 0);
        return count + " units available";
    }

    public static synchronized String purchaseItem(String productId, int userId) {

        int count = stock.getOrDefault(productId, 0);

        if (count > 0) {
            stock.put(productId, count - 1);
            return "Success, " + (count - 1) + " units remaining";
        } else {
            waitingList.putIfAbsent(productId, new LinkedList<>());
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }

    public static void main(String[] args) {

        stock.put("IPHONE15_256GB", 100);

        System.out.println(checkStock("IPHONE15_256GB"));

        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        for (int i = 0; i < 100; i++) {
            purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(purchaseItem("IPHONE15_256GB", 99999));
    }
}
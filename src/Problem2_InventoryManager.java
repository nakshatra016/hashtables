import java.util.*;

/*
 Problem 2: E-commerce Flash Sale Inventory Manager
 Concepts Used:
 - HashMap for O(1) lookup
 - Synchronized method for safe stock updates
 - Queue for waiting list (FIFO)
*/

public class Problem2_InventoryManager {

    // productId -> stock count
    private HashMap<String, Integer> inventory;

    // productId -> waiting list queue
    private HashMap<String, Queue<Integer>> waitingList;

    public Problem2_InventoryManager() {
        inventory = new HashMap<>();
        waitingList = new HashMap<>();
    }

    // Add product with stock
    public void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }

    // Check stock in O(1)
    public int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    // Purchase item (synchronized to prevent overselling)
    public synchronized String purchaseItem(String productId, int userId) {

        if (!inventory.containsKey(productId)) {
            return "Product does not exist.";
        }

        int stock = inventory.get(productId);

        if (stock > 0) {
            inventory.put(productId, stock - 1);
            return "Purchase successful. Remaining stock: " + (stock - 1);
        } else {
            waitingList.get(productId).add(userId);
            return "Out of stock. Added to waiting list. Position: "
                    + waitingList.get(productId).size();
        }
    }

    // Display waiting list
    public void showWaitingList(String productId) {
        Queue<Integer> queue = waitingList.get(productId);
        System.out.println("Waiting list for " + productId + ": " + queue);
    }

    // Main method for testing
    public static void main(String[] args) {

        Problem2_InventoryManager manager = new Problem2_InventoryManager();

        manager.addProduct("IPHONE15_256GB", 3);

        System.out.println("Stock: "
                + manager.checkStock("IPHONE15_256GB"));

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 101));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 102));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 103));

        // Stock should now be 0
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 104));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 105));

        manager.showWaitingList("IPHONE15_256GB");
    }
}
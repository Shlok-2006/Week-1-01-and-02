import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;

    Transaction(int id, int amount, String merchant) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
    }
}

public class TwoSumTransactions {

    public static void findTwoSum(List<Transaction> list, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                System.out.println("(" + map.get(complement).id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    public static void detectDuplicates(List<Transaction> list) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : list) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String k : map.keySet()) {

            if (map.get(k).size() > 1) {
                System.out.println("Duplicate: " + map.get(k));
            }
        }
    }

    public static void main(String[] args) {

        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction(1, 500, "StoreA"));
        list.add(new Transaction(2, 300, "StoreB"));
        list.add(new Transaction(3, 200, "StoreC"));

        findTwoSum(list, 500);
        detectDuplicates(list);
    }
}
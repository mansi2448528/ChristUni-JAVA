import java.util.*;
import java.util.concurrent.*;

public class Lab6_que1 {

    // Function to calculate all possible combinations using dynamic programming
    public static List<List<Integer>> findCombinations(int[] coins, int sum) {
        List<List<List<Integer>>> dp = new ArrayList<>(sum + 1);

        // Initialize dp array with empty lists
        for (int i = 0; i <= sum; i++) {
            dp.add(new ArrayList<>());
        }

        // Base case: There's one way to make sum 0, which is using no coins
        dp.get(0).add(new ArrayList<>());

        // Loop through each coin
        for (int coin : coins) {
            // For each sum from coin to the target sum
            for (int i = coin; i <= sum; i++) {
                // Add the coin to all the combinations of (i - coin)
                for (List<Integer> combination : dp.get(i - coin)) {
                    List<Integer> newCombination = new ArrayList<>(combination);
                    newCombination.add(coin);
                    dp.get(i).add(newCombination);
                }
            }
        }

        return dp.get(sum); // Return all combinations for the target sum
    }

    // Function to divide the work into smaller subproblems and execute them using multithreading
    public static List<List<Integer>> findCombinationsUsingThreads(int[] coins, int sum) throws InterruptedException, ExecutionException {
        // Number of threads to divide the task into
        int numThreads = 4; // Example: 4 threads for better performance
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // List of callable tasks that will compute combinations for sub-ranges
        Callable<List<List<Integer>>> task = () -> findCombinations(coins, sum);

        // Submit the task to the executor
        Future<List<List<Integer>>> result = executor.submit(task);

        // Wait for the result
        List<List<Integer>> combinations = result.get();

        // Shutdown the executor
        executor.shutdown();

        return combinations;
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 3};
        int sum = 8;

        try {
            List<List<Integer>> combinations = findCombinationsUsingThreads(coins, sum);
            System.out.println("Number of ways to make sum " + sum + ": " + combinations.size());
            System.out.println("Possible combinations:");
            for (List<Integer> combination : combinations) {
                System.out.println(combination);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

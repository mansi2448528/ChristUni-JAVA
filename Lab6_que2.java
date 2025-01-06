class CounterEmptyException extends Exception {
    public CounterEmptyException(String message) {
        super(message);
    }
}

// CoffeeShop class to manage the counter and tasks
class CoffeeShop {
    private int counter = 0;
    private final int MAX_CAPACITY = 3;

    // Method for baristas (producers) to prepare coffee
    public synchronized void prepareCoffee(String baristaName) throws InterruptedException {
        while (counter == MAX_CAPACITY) {
            System.out.println(baristaName + " is waiting. Counter is full.");
            wait();  // Wait if the counter is full
        }
        counter++;
        System.out.println(baristaName + " prepared coffee. Counter: " + counter);
        notifyAll();  // Notify customers or reviewer after adding coffee
    }

    // Method for customers (consumers) to pick up coffee
    public synchronized void pickUpCoffee(String customerName) throws InterruptedException, CounterEmptyException {
        while (counter == 0) {
            System.out.println(customerName + " is waiting. Counter is empty.");
            wait();  // Wait if the counter is empty
        }

        counter--;
        System.out.println(customerName + " picked up coffee. Counter: " + counter);
        notifyAll();  // Notify baristas or reviewer after picking up coffee
    }

    // Method for reviewer (observer) to sample coffee
    public synchronized void sampleCoffee() throws InterruptedException, CounterEmptyException {
        while (counter == 0) {
            System.out.println("Reviewer is waiting. Counter is empty.");
            wait();  // Wait if the counter is empty
        }

        counter--;
        System.out.println("Coffee Reviewer sampled coffee. Counter: " + counter);
        notifyAll();  // Notify baristas or customers after reviewing
    }
}

// Barista (Producer) class
class Barista extends Thread {
    private CoffeeShop coffeeShop;
    private String baristaName;
    private int coffeeCount;

    public Barista(CoffeeShop coffeeShop, String baristaName, int coffeeCount) {
        this.coffeeShop = coffeeShop;
        this.baristaName = baristaName;
        this.coffeeCount = coffeeCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < coffeeCount; i++) {
                coffeeShop.prepareCoffee(baristaName);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Customer (Consumer) class
class Customer extends Thread {
    private CoffeeShop coffeeShop;
    private String customerName;
    private int coffeeCount;

    public Customer(CoffeeShop coffeeShop, String customerName, int coffeeCount) {
        this.coffeeShop = coffeeShop;
        this.customerName = customerName;
        this.coffeeCount = coffeeCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < coffeeCount; i++) {
                coffeeShop.pickUpCoffee(customerName);
            }
        } catch (InterruptedException | CounterEmptyException e) {
            e.printStackTrace();
        }
    }
}

// Coffee Reviewer (Observer) class
class CoffeeReviewer extends Thread {
    private CoffeeShop coffeeShop;

    public CoffeeReviewer(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            coffeeShop.sampleCoffee();
        } catch (InterruptedException | CounterEmptyException e) {
            e.printStackTrace();
        }
    }
}

// Main class
public class Lab6_que2 {
    public static void main(String[] args) {
        // Create CoffeeShop instance
        CoffeeShop coffeeShop = new CoffeeShop();

        // Create Baristas (producers)
        Barista barista1 = new Barista(coffeeShop, "Barista 1", 2);
        Barista barista2 = new Barista(coffeeShop, "Barista 2", 3);

        // Create Customers (consumers)
        Customer customer1 = new Customer(coffeeShop, "Customer 1", 1);
        Customer customer2 = new Customer(coffeeShop, "Customer 2", 2);
        Customer customer3 = new Customer(coffeeShop, "Customer 3", 1);

        // Create Coffee Reviewer (observer)
        CoffeeReviewer reviewer = new CoffeeReviewer(coffeeShop);

        // Start threads
        barista1.start();
        barista2.start();
        customer1.start();
        customer2.start();
        customer3.start();
        reviewer.start();
    }
}

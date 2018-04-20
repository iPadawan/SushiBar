import java.util.concurrent.ThreadLocalRandom;


/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {
    private int id = 0;
    private static int count = 0;
    private int barOrders;
    private int takeawayOrders;
    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    public Customer() {
        this.id = count++;
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized int order(){
        // Should be replaced with a randomizer that allows either number to be maxOrder.
        barOrders = ThreadLocalRandom.current().nextInt(0, (int)(SushiBar.maxOrder));
        takeawayOrders = (ThreadLocalRandom.current().nextInt(0, (int)(SushiBar.maxOrder/2 + 1)));
        SushiBar.servedOrders.add(barOrders);
        SushiBar.takeawayOrders.add(takeawayOrders);
        SushiBar.totalOrders.add(barOrders + takeawayOrders);
        return barOrders;
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.id;
    }

    // Add more methods as you see fit
}

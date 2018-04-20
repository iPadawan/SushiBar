/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */

public class Waitress implements Runnable {
    private WaitingArea waitingArea;
    private Customer customer;
    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        while (SushiBar.isOpen || !waitingArea.isEmpty()) {

            customer = waitingArea.next();

            /*
             * CUSTOMER FETCHED: NOW WE WAIT...
             */
            if (customer != null) {
                SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID()
                        + " is now fetched.");
                // The waitress uses some time before taking the customer's order.
                try {
                    Thread.sleep((long) (SushiBar.waitressWait));
                } catch (InterruptedException e) {
                }


                /*
                 * CUSTOMER EATING: NOW WE WAIT...
                 */
                SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID()
                        + " is now eating.");
                try {
                    // Customer order returns the number of orders to be eaten in the bar.
                    Thread.sleep((long) (customer.order()) * SushiBar.customerWait);
                } catch (InterruptedException e) {
                }


                /*
                 * CUSTOMER DONE: GET HIM OUT OF HERE...
                 */
                SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID()
                        + " is now leaving.");
           }
        }
        System.out.println("Waitress.run");
    }
}

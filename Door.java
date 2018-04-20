import static java.lang.Thread.sleep;

/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {
    WaitingArea waitingArea;
    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the customerQueue
     */
    @Override
    public void run() {
        while (SushiBar.isOpen) {
            waitingArea.enter(this.generateCustomer());

            try {
                Thread.sleep((long) (SushiBar.doorWait));
            } catch (InterruptedException e) {
            }
        }
        synchronized (waitingArea){
            waitingArea.notifyAll();
        }
        System.out.println("Door.run");
    }

    private Customer generateCustomer() {
        Customer customer = new Customer();
        SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID()
        + " is now waiting.");
        return customer;
    }

}

import java.util.Queue;
import java.util.LinkedList;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {
    private int areaSize;
    private Queue<Customer> customers;
    /**
     * Creates a new waiting area.
     * areaSize decides how many people can be waiting at the same time (how large the shared buffer is)
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        this.areaSize = size;
        customers = new LinkedList<Customer>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        while (this.isFull()) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (SushiBar.isOpen) {
            this.notify();
            SushiBar.customerCounter.increment();
            customers.add(customer);
        }
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {

        while (this.isEmpty() && SushiBar.isOpen) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!this.isEmpty()) {
            this.notify();
            SushiBar.customerCounter.decrement();
            return customers.remove();
        }
        return null;
    }

    public synchronized boolean isFull() {
        return (customers.size() == areaSize);
    }

    public synchronized boolean isEmpty() {
        return (customers.size() == 0);
    }
}

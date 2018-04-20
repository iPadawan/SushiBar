import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 10;
    private static int duration = 10; // For clock (presumably)
    public static int maxOrder = 10; // Referenced by customer class
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 300; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);
        new Clock(duration);
        Thread doorThread = new Thread(new Door(waitingArea));
        doorThread.start();

        ArrayList<Thread> waitresses = new ArrayList<>();

        for (int i = 0; i < waitressCount; i++)
        {
            Thread waitressThread = new Thread(new Waitress(waitingArea));
            waitresses.add(waitressThread);
            waitressThread.start();
        }
        try {
            doorThread.join();
            for (int i = 0; i < waitressCount; i++)
            {
                waitresses.get(i).join();
            }
        }
        catch(InterruptedException e){}

        SushiBar.write("Bar is now closed...");
        SushiBar.write("Total orders: " + SushiBar.totalOrders.get());
        SushiBar.write("Total takeaways: " + SushiBar.takeawayOrders.get());
        SushiBar.write("Total bar orders: " + SushiBar.servedOrders.get());
    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

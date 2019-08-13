import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {

    public Buyer(int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog,
            int iteration) {
        setSleepTime(sleepTime);
        // TODO: Find use of catalogSize
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.catalog = catalog;
        setIteration(iteration);
    }

    public void buy() throws InterruptedException {
        lock.lock();
        try {
            while (catalog.isEmpty()) {
                /*not*/empty.await();
            }
            NodeBase<V> n = catalog.dequeue();
            assert n != null;
            System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
            n.show(); // DO NOT REMOVE (For Automated Testing)

            if (!catalog.isFull()) {
                /*not*/full.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

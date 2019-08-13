import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {

    public Seller(int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog,
            Queue<V> inventory) {
        setSleepTime(sleepTime);
        // TODO: find use of catalogSize
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.catalog = catalog;
        this.inventory = inventory;
    }

    public void sell() throws InterruptedException {
        if (inventory.isEmpty()) {
            return;
        }

        lock.lock();
        try {
            while (catalog.isFull()) {
                /*not*/full.await();
            }
            if (inventory.isEmpty()) {
                return;
            }
            Node<V> node = (Node<V>) inventory.dequeue();
            /* System.out.println("Enqueued " + node.getPriority()); */
            catalog.enqueue(node);
            /*not*/empty.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

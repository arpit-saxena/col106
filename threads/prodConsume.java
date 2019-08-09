import java.util.Random;

class Data {
    private int data = 0;
    private boolean empty = true;

    public synchronized int getData() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = true;
        notifyAll();
        return data;
    }

    public synchronized void setData(int data) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false;
        notifyAll();
        this.data = data;
    }

    public synchronized boolean isEmpty() {
        return empty;
    }
}

class Producer implements Runnable {
    private Data data;
    Random random = new Random();

    public Producer(Data data) {
        this.data = data;
    }

    public void run() {
        while (true) {
            int a = random.nextInt(10);
            data.setData(a);
            System.out.println("[Producer] Set " + a);
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {}
        }
    }
}

class Consumer implements Runnable {
    private Data data;
    Random random = new Random();

    public Consumer(Data data) {
        this.data = data;
    }

    public void run() {
        while (true) {

            int a = data.getData();
            System.out.println("[Consumer] Got " + a);

            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {}
        }
    }
}

class Main {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(new Producer(data)).start();
        new Thread(new Consumer(data)).start();
    }
}
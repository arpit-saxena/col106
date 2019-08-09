class Q{
    int n;
    boolean valSet = false;

    synchronized int get(){
        while(!valSet){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Got: " + n);
        valSet = false;
        notify();
        return n;
    }

    synchronized void put(int n){
        while(valSet){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.n = n;
        valSet = true;
        System.out.println("Put: " + n);
        notify();
    }
}

class Producer implements Runnable{
    Q q;
    Thread t;
    Producer(Q q){
        this.q = q;
        t = new Thread(this);
        t.start();
    }

    public void run(){
        int i=0;
        while(true){
            q.put(i++);
        }
    }
}

class Consumer implements Runnable{
    Q q;
    Thread t;

    Consumer(Q q){
        this.q = q;
        t = new Thread(this);
        t.start();
    }

    public void run(){
        while(true){
            q.get();
        }
    }
}

public class Demonstration_8{
    public static void main(String args[]) throws InterruptedException{
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
    }
}
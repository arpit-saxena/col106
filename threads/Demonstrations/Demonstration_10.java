import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

class Receiver{
	void call(String msg){
		System.out.print("[" + msg);
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException e){
			System.out.print("interrupted");
		}
		System.out.print("]");
	}
}

class Caller implements Runnable{
	Thread t;
	String msg;
	Receiver receiver;
	public Lock lck; 
	
	Caller(String msg, Receiver r, Lock lck){
		this.receiver = r;
		this.msg = msg;
		t = new Thread(this);
		t.start();
		this.lck = lck;
	}
	
	public void run() {
		try{
			this.lck.lock();
			this.receiver.call(this.msg);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			this.lck.unlock();
		}
	}
}


public class Demonstration_10 {
	public static void main(String args[]){
		Receiver r = new Receiver();
		Lock lck = new ReentrantLock();
		Caller c1 = new Caller("Hello", r, lck);
		Caller c2 = new Caller("Synchronized", r, lck);
		Caller c3 = new Caller("Method", r, lck);
		
		
	}
}

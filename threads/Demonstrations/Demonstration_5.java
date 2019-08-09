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
	
	Caller(String msg, Receiver r){
		this.receiver = r;
		this.msg = msg;
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
			this.receiver.call(this.msg);
	}
}


public class Demonstration_5 {
	public static void main(String args[]){
		Receiver r = new Receiver();
		Caller c1 = new Caller("Hello", r);
		Caller c2 = new Caller("Synchronized", r);
		Caller c3 = new Caller("Method", r);
		try {
			c1.t.join();
			c2.t.join();
			c3.t.join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

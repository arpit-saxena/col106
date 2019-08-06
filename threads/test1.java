class Runnable1 implements Runnable {
	public void run() {
		for (int i = 0; i < 5; i++)
		{
			System.out.println("A: " + i);
		}
		System.out.println("Exiting thread A");
	}
}

class Runnable2 implements Runnable {
	public void run() {
		for (int i = 0; i < 5; i++)
		{
			System.out.println("B: " + i);
		}
		System.out.println("Exiting thread B");
	}
}

class Runnable3 implements Runnable {
	public void run() {
		for (int i = 0; i < 5; i++)
		{
			System.out.println("C: " + i);
		}
		System.out.println("Exiting thread C");
	}
}

class test {
	public static void main(String[] args) {
		Runnable1 r1 = new Runnable1();
		Runnable2 r2 = new Runnable2();
		Runnable3 r3 = new Runnable3();
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);

		t1.start(); t2.start(); t3.start();
		System.out.println("Exiting main..");
	}

}

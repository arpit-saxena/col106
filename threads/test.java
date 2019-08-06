class ThreadA extends Thread {
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread A: " + i);	
			}
			System.out.println("Exiting from Thread A...");
		}
}

class ThreadB extends Thread {
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread B: " + i);	
			}
			System.out.println("Exiting from Thread B...");
		}
}


class ThreadC extends Thread {
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread C: " + i);	
			}
			System.out.println("Exiting from Thread C...");
		}
}

class test {
	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		ThreadB b = new ThreadB();
		ThreadC c = new ThreadC();
		a.start(); b.start(); c.start();
		System.out.println("Exiting from main function");
	}
}

class ThreadA extends Thread {
		public void run() {
			System.out.println("Start Thread A");
			for (int i = 0; i < 5; i++) {
				if (i == 0) yield();
				System.out.println("Thread A: " + i);	
			}
			System.out.println("Exiting from Thread A...");
		}
}

class ThreadB extends Thread {
		public void run() {
			System.out.println("Start Thread B");
			for (int i = 0; i < 5; i++) {
				if (i == 1) stop();
				System.out.println("Thread B: " + i);	
			}
			System.out.println("Exiting from Thread B...");
		}
}


class ThreadC extends Thread {
		public void run() {
			System.out.println("Start Thread C");
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread C: " + i);
				if (i == 2) {
					try {
						sleep(1000);
					} catch (Exception e) {}
				}
			}
			System.out.println("Exiting from Thread C...");
		}
}

class test {
	public static void main(String[] args) throws Exception{
		ThreadA a = new ThreadA();
		ThreadB b = new ThreadB();
		ThreadC c = new ThreadC();
		a.start(); b.start(); b.join(); c.start();
		System.out.println("Exiting from main function");
	}
}

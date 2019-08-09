class ThreadA extends Thread{
    public void run(){
        for(int i=1; i<=5; ++i){
            System.out.println("From Thread A with i = " + -1*i);
        }
        System.out.println("Exiting from Thread A");
    }
    
}

class ThreadB extends Thread{
    public void run(){
        for(int i=1; i<=5; ++i){
            System.out.println("From Thread B with i = " + 2*i);
        }
        System.out.println("Exiting from Thread B");
    }
}

class ThreadC extends Thread{
    public void run(){
        for(int i=1; i<=5; ++i){
            System.out.println("From Thread C with i = " + (2*i-1));
        }
        System.out.println("Exiting from Thread C");
    } 
}

public class Demonstration_1{
    public static void main(String args[]) throws Exception{
        Thread a = new ThreadA();
        Thread b = new ThreadB();
        Thread c = new ThreadC();
        a.start();
        b.start();
        b.join();
        c.start();
        System.out.println("Multithreading over");
    }
}
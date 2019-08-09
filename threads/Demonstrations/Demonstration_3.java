class ThreadA implements Runnable{
    public void run(){
        for(int i=1; i<=5; ++i){
            if(i==1){
                Thread.yield();
            }
            System.out.println("From Thread A with i = " + -1*i);
        }
        System.out.println("Exiting from Thread A");
    }
    
}

class ThreadB implements Runnable{
    public void run(){
        for(int i=1; i<=5; ++i){
            if(i == 2){
                //stop();
            }
            System.out.println("From Thread B with i = " + 2*i);
        }
        System.out.println("Exiting from Thread B");
    }
}

class ThreadC implements Runnable{
    public void run(){
        for(int i=1; i<=5; ++i){
            try{
                if(i==3){
                    Thread.sleep(1000);
                }
            }
            catch(Exception e){

            }
            System.out.println("From Thread C with i = " + (2*i-1));
        }
        System.out.println("Exiting from Thread C");
    } 
}

public class Demonstration_3{
    public static void main(String args[]){
        Thread a = new Thread(new ThreadA());
        Thread b = new Thread(new ThreadB());
        Thread c = new Thread(new ThreadC());
        a.start();
        b.start();
        c.start();
        System.out.println("Multithreading over");
    }
}
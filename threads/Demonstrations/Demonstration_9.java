class Sum extends Thread {
    public long begin, end;
    public Sum(long b, long e) {
        this.begin = b;
        this.end = e;
    }

    public void run(){
        long sum = 0;
        for(long i = this.begin; i<=this.end; i++){
                sum += i;
        }
    }
}

public class Demonstration_9 {
    public static void main(String args[]) {
        if(args.length != 1){
            System.out.println("Number of threads required as argument");
            return;
        }
        int numThreads = Integer.parseInt(args[0]);
        long start = 1;
        long finish = 1000000000;
        long interval = finish/numThreads;

        for(int i=0; i<numThreads; i++) {
            Sum s = new Sum( (interval*i+start), interval*(i+1) );
            s.start();
        }
    }
}
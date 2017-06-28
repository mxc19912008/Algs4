import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Permutation {
    public static void main(String[] args){
        int k = Integer.valueOf(args[0]); 
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            rq.enqueue(s);
        }      
        for (int i = 0; i < k; i++) {
            String sample = rq.dequeue();
            StdOut.println(sample);
        }
     }
}
    

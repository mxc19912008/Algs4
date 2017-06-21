import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] it;
    private int N;
    
    public RandomizedQueue(){                 // construct an empty randomized queue
        it = (Item[]) new Object[1];
        N = 1;
    }
    public boolean isEmpty(){                 // is the queue empty?
        return N == 1;
    }
    public int size(){                        // return the number of items on the queue
        return N-1;
    }
    public void enqueue(Item item){           // add the item
        if(item == null){
                throw new java.lang.NullPointerException();
        }
        //System.out.println("The length of the String is "+it.length);
        //System.out.println("N is "+N);
        if(N == it.length){
            resize(2 * N);
        }
        it[N] = item;
        N++;
        //System.out.println("After adding, N is "+N);
    }
    public Item dequeue(){                    // remove and return a random item   
        if(N <= 1){
            throw new java.util.NoSuchElementException();
        }
        int pos = 1;
        Item toReturn = it[pos];
        if(N > 2){
            pos = StdRandom.uniform(1, N);
            toReturn = it[pos];
            it[pos] = it[N-1];
            it[N-1] = null;
        }
        N--;
        if(N > 0 && N == it.length / 4){
            resize(it.length / 2);
        }
        /*for(int i=0; i<=N; i++){
            System.out.println("it"+i+" "+it[i]);
        }*/
        return toReturn;
    }
    private void resize(int n){
        Item[] copy = (Item[]) new Object[n];
        for(int i = 0; i < N; i++){
            copy[i] = it[i];
        }
        it = copy;
    }
    public Item sample(){                     // return (but do not remove) a random item
        if(N <= 1){
            throw new java.util.NoSuchElementException();
        }
        int pos = 1;
        Item toReturn = it[pos];
        if(N > 2){
            pos = StdRandom.uniform(1, N);
            toReturn = it[pos];
        }
        return toReturn;
    }
    public Iterator<Item> iterator(){         // return an independent iterator over items in random order
        return new ArrayInterator();
    }
    private class ArrayInterator implements Iterator<Item>{
         private int i;
         private int[] indexes;
         
         public ArrayInterator() {
             i = 1;
             indexes = new int[];
         }
    
         public boolean hasNext()  { 
             return i < N;      
         }
         public void remove() { 
             throw new java.lang.UnsupportedOperationException();
         }
         
         public Item next() {
             if (!hasNext()) { 
                 throw new java.util.NoSuchElementException();
             }
            int pos = StdRandom.uniform(i, N);
            i++;
            return it[pos];
            
         }
    }
    public static void main(String[] args){   // unit testing (optional)
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.print(rq.dequeue());
                //StdOut.print("The size is "+rq.size());
            }
            else if(s.equals("*")){
                StdOut.print(rq.sample());
                //StdOut.print("The size is "+rq.size());
            }
            else rq.enqueue(s);
       }
    }
}
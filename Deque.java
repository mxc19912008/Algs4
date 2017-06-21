import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int N;
    private class Node{
        Item item;
        Node next;
        Node previous;
    }
    public Deque(){                           // construct an empty deque
        first = null;
        last = null;
        N = 0;
    }
    public boolean isEmpty(){                 // is the deque empty?
        return N == 0;
    }
    public int size(){                        // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item){          // add the item to the front
        if(item == null){
            throw new java.lang.NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.previous = null;
        first.item = item;
        if(isEmpty()){
            first.next = null;
            last = first;
        }else{
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        N++;
    }
    public void addLast(Item item){           // add the item to the end
        if(item == null){
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()){
            last.previous = null;
            first = last;
        }else{
            oldLast.next = last;
            last.previous = oldLast;
        }
        N++;
    }
    public Item removeFirst(){                // remove and return the item from the front
        if(N == 0){
            throw new java.util.NoSuchElementException();
        }
        Item toRemove = first.item;
        if(N == 1){
            first = null;
            last = null;
        }else{
            first = first.next;
            first.previous = null;
        }
        N--;
        return toRemove;
    }
    public Item removeLast(){                 // remove and return the item from the end
        if(N == 0){
            throw new java.util.NoSuchElementException();
        }
        Item toRemove = last.item;
        if(N == 1){
            first = null;
            last = null;
        }else{
            last = last.previous;
            last.next = null;
        }
        N--;
        return toRemove;
    }
    public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
        return new ListInterator();
    }
    private class ListInterator implements Iterator<Item>{
        private Node current = first;
        
        public boolean hasNext(){
            return current != null;
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next(){
            if(!hasNext()){
                throw new java.util.NoSuchElementException();
            }
            Item item  = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args){   // unit testing (optional)
        Deque<String> dq = new Deque<String>();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.print(dq.removeLast());
                StdOut.print("After removal, the size is "+dq.size());
            }
            else
            dq.addFirst(s);
            //dq.addLast(s);
        }

    }
}
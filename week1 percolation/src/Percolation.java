/**
 * Write a description of Percolation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int num; // the number of grids;
   private boolean[][] site;
   private WeightedQuickUnionUF uf;
   private WeightedQuickUnionUF ufForBackWash;
   
   
   public Percolation(int n){    // create n-by-n grid, with all sites blocked
       if (n<=0) {
            throw new IllegalArgumentException();
       }
       num = n;
       site = new boolean[n][n];
       uf = new WeightedQuickUnionUF(n*n+2);
       ufForBackWash = new WeightedQuickUnionUF(n*n+2);
   }                
   public    void open(int row, int col){    // open site (row, col) if it is not open already
       if ((row < 1) || (row > num) || (col < 1) || (col > num)) {
            throw new IndexOutOfBoundsException();
         }
       site[row-1][col-1]=true;
       int pos = (row-1) * num+ col-1;
       if(row==1){
           uf.union(pos,num*num);
           ufForBackWash.union(pos,num*num);
       }
       if(row==num){
           uf.union(pos,num*num+1);
       }
       
       if(row!=1&&isOpen(row-1,col)){
           int upper = pos - num;
           uf.union(pos,upper);
           ufForBackWash.union(pos,upper);
       }
       if(row!=num&&isOpen(row+1,col)){
           int lower = pos + num;
           uf.union(pos,lower);
           ufForBackWash.union(pos,lower);
       }
       if(col!=1&&isOpen(row,col-1)){
           int left = pos - 1;
           uf.union(pos,left);
           ufForBackWash.union(pos,left);
       }
       if(col!=num&&isOpen(row,col+1)){
           int right = pos + 1;
           uf.union(pos,right);
           ufForBackWash.union(pos,right);
        }
        
   }
   public boolean isOpen(int row, int col){  // is site (row, col) open?
        if ((row < 1) || (row > num) || (col < 1) || (col > num)) {
            throw new IndexOutOfBoundsException();
         }
       return site [row-1][col-1];
   }
   public boolean isFull(int row, int col){  // is site (row, col) full?
        if ((row < 1) || (row > num) || (col < 1) || (col > num)) {
            throw new IndexOutOfBoundsException();
         }
        int pos = (row-1) * num+ col-1;
       return ufForBackWash.connected(num * num, pos);
   }
   public     int numberOfOpenSites(){       // number of open sites
       int sum = 0;
       for(int i=1;i<=num;i++){
           for(int j=1;j<=num;j++){
               if(isOpen(i,j)||isFull(i,j)){
                   sum += 1;
               }
           }
       }
       return sum;
   }
   public boolean percolates(){              // does the system percolate?
       int top = num*num;
       int bottom = num*num+1;
       return uf.connected(top,bottom);
   }

   public static void main(String[] args){   // test client (optional)
   }
}


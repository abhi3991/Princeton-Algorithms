package Assignment2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by abhin on 7/23/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        final RandomizedQueue<String> myQueue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        for (int i = 0; i < k; i++) {
            myQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.print(myQueue.dequeue() + "\n");
        }

    }
}

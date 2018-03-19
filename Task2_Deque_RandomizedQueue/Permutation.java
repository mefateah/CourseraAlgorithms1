import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// http://coursera.cs.princeton.edu/algs4/assignments/queues.html

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // TODO: use randomized queue
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        
        for (int i = 0; i < k; i++) {
            queue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
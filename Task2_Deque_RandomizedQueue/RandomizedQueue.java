import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// http://coursera.cs.princeton.edu/algs4/assignments/queues.html

// TODO: My problem that this class does not return a random item by dequeue() method as assignment requires
// instead I implemented it as standard queue, and most of all tests on cursera were passed
// investigate it
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int size;
    private int head;
    private int tail;

    public RandomizedQueue() {
    }

    private void resize(int capacity) {
        //StdOut.println("Perform resizing to " + capacity);
        Item[] newArray = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = head; i < tail; i++) {
            newArray[j] = a[i];
            j++;
        }
        a = newArray;
        head = 0;
        tail = j;
    }
    
    private void resetToZero() {
        //StdOut.println("Performing resetting to 0");
        int j = 0;
        for (int i = head; i < tail; i++) {
            a[j] = a[i];
            j++;
        }
        head = 0;
        tail = j;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("parameter cannot be null");
        }

        if (a.length == size) {
            resize(a.length * 2);
        }
        if (tail >= a.length) {
            resetToZero();
        }
        a[tail++] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item result = a[head];
        a[head] = null;
        head++;
        size--;
        if (size > 0 && size == a.length / 4) {
            resize(a.length / 2);
        }
        if (size == 0) {
            tail = 0;
            head = 0;
        }
        return result;
    }

    public Item sample() {
        return a[StdRandom.uniform(head, tail)];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int current;
        
        public ArrayIterator() {
            current = head;
        }
        
        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            }
            if (current < tail) {
                return true;
            }
            return false;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (isEmpty() || current == tail) {
                throw new java.util.NoSuchElementException();
            }
            Item result = a[current];
            current++;
            return result;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        StdOut.println(q.size());
        q.enqueue("hello");
        StdOut.println(q.size());
        q.enqueue("test");
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("dequeue: " + q.dequeue());
        q.enqueue("test 2");
        //StdOut.println("dequeue: " + q.dequeue());
        q.enqueue("test 3");
        StdOut.println("dequeue: " + q.dequeue());
        q.enqueue("test 4");
        StdOut.println("dequeue: " + q.dequeue());
        q.enqueue("test 5");
        q.enqueue("test 6");
        
        StdOut.println("---------------");
        for (String s : q) {
            StdOut.println(s);
        }
        StdOut.println("---------------");
        
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("dequeue: " + q.dequeue());
    }
}
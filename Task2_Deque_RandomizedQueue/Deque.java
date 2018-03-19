import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

// http://coursera.cs.princeton.edu/algs4/assignments/queues.html

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int size;

    public Deque() {

    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private void checkInputParam(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("parameter cannot be null");
        }
    }

    public boolean isEmpty() {
        if (first == null) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkInputParam(item);

        Node node = new Node(item, null, null);
        if (first == null) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.previous = node;
            first = node;
        }
        size++;
    }

    public void addLast(Item item) {
        checkInputParam(item);

        Node node = new Node(item, null, null);
        if (last == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
            node.previous = last;
            last = node;
        }
        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new java.util.NoSuchElementException();
        }
        Node toRemove = first;
        if (first == last) {
            first = last = null;
        } else {
            first = first.next;
            first.previous = null;
            toRemove.next = null;
        }
        size--;
        return toRemove.item;
    }

    public Item removeLast() {
        if (first == null) {
            throw new java.util.NoSuchElementException();
        }
        Node toRemove = last;
        if (first == last) {
            first = last = null;
        } else {
            last = last.previous;
            last.next = null;
            toRemove.previous = null;
        }
        size--;
        return toRemove.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        for (int i = 1; i <= 3; i++) {
            StdOut.println(i);
            StdOut.println("isEmpty: " + d.isEmpty());
            StdOut.println("size: " + d.size());
            d.addLast(String.valueOf(i));
        }
        
        StdOut.println("---------------");
        StdOut.println("foreach check:");
        for (String s : d) {
            StdOut.println(s);
        }
        StdOut.println("---------------");
        
        StdOut.println(d.removeLast());
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("size: " + d.size());
        StdOut.println(d.removeLast());
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("size: " + d.size());
        StdOut.println(d.removeFirst());
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("size: " + d.size());
    }
}
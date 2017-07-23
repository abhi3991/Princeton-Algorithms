package Assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by abhin on 7/23/2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    /**
     * construct an empty deque
     */
    public Deque() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    /**
     * return an iterator over items in order from front to end
     *
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new CustomIterator<>();
    }

    private class CustomIterator<Item> implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            final Item item = (Item) current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported operation!!");
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /**
     * is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * add the item to the front
     *
     * @param item
     */
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("null element cannot be accepted!!");
        }
        if (isEmpty()) {
            first = last = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            size++;
            return;
        }
        {
            final Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.prev = null;
            first.next = oldFirst;
            oldFirst.prev = first;
            size++;
            return;
        }
    }


    /**
     * add the item to the end
     *
     * @param item
     */
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("null element cannot be accepted!!");
        }

        if (isEmpty()) {
            first = last = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            size++;
            return;
        }
        {
            final Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;
            size++;
            return;
        }
    }

    /**
     * remove and return the item from the front
     *
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Element cannot be removed from an empty queue!!");
        }

        final Item x = first.item;
        if (size() == 1) {
            first = last = null;
            size--;
            return x;
        }

        {
            first = first.next;
            first.prev = null;
            size--;
            return x;
        }
    }

    /**
     * remove and return the item from the end
     *
     * @return
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Element cannot be removed from an empty queue!!");
        }
        final Item x = last.item;

        if (size() == 1) {
            first = last = null;
            size--;
            return x;
        }
        {
            last = last.prev;
            last.next = null;
            size--;
            return x;
        }
    }

    /**
     * unit testing (optional)
     *
     * @param args
     */
    public static void main(String[] args) {

        final Deque<String> myQueue = new Deque<>();
        myQueue.addFirst("your");
        myQueue.addFirst("is");
        myQueue.addLast("code");
        myQueue.addLast("working");
        myQueue.addLast("fine ??");

        for (final String x : myQueue) {
            System.out.print(x + " ");
        }

        System.out.println("\nTested adding!! ========= testing removing!!");

        myQueue.removeFirst();
        myQueue.removeFirst();
        myQueue.removeLast();

        for (final String x : myQueue) {
            System.out.print(x + " ");
        }

    }

}

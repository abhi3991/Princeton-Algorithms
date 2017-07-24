package Assignment2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by abhin on 7/23/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N;
    private Item[] items;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.N = 0;
        this.items = (Item[]) new Object[1];
    }

    /**
     * unit testing (optional)
     *
     * @param args
     */
    public static void main(String[] args) {

        final RandomizedQueue<String> myQueue = new RandomizedQueue<>();
        myQueue.enqueue("is");
        myQueue.enqueue("your");
        myQueue.enqueue("code");
        myQueue.enqueue("working");
        myQueue.enqueue("fine ??");

        for (final String x : myQueue) {
            System.out.print(x + " ");
        }

        System.out.println("\nTested adding!! ========= testing removing!!");

        myQueue.dequeue();
        myQueue.dequeue();
        myQueue.dequeue();

        for (final String x : myQueue) {
            System.out.print(x + " ");
        }

        for (final String x : myQueue) {
            System.out.print(x + " ");
        }

    }

    /**
     * is the queue empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return this.N == 0;
    }

    /**
     * return the number of items on the queue
     *
     * @return
     */
    public int size() {
        return this.N;
    }

    /**
     * add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot insert a null element!!");
        }
        if (N == items.length) {
            resize(2 * items.length);
        }
        this.items[N++] = item;
    }

    private void resize(int capacity) {
        final Item[] copyItems = (Item[]) new Object[capacity];

        for (int i = 0; i < N; i++) {
            copyItems[i] = items[i];
        }
        this.items = copyItems;
    }

    /**
     * remove and return a random item
     *
     * @return
     */
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!!");
        }
        final int random = StdRandom.uniform(N);
        final Item item = items[random];
        for (int i = random; i < N; i++) {
            if (i + 1 != N)
                items[i] = items[i + 1];
        }
        items[--N] = null;
        if (N > 0 && N == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    /**
     * return (but do not remove) a random item
     *
     * @return
     */
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!!");
        }
        final int random = StdRandom.uniform(N);
        return items[random];
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return
     */
    public Iterator<Item> iterator() {

        return new CustomIterator<>();

    }

    private class CustomIterator<Item> implements Iterator<Item> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current != N;
        }

        @Override
        public Item next() {
            if (current == N) {
                throw new NoSuchElementException("No elements to retrieve!!");
            }
            return (Item) items[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }
    }

}

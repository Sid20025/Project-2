package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a circular linked list.
 * This class extends the List class to provide additional functionalities specific to circular traversal.
 *
 * @param <E> Type of elements in the circular linked list.
 */
public class CircularList<E> extends List<E> {
    private E head; // The head element of the circular linked list.

    /**
     * Creates an empty circular linked list.
     * Initializes the head as null, indicating no head element.
     */
    public CircularList() {
        super();
        head = null;
    }

    /**
     * Sets the head of the linked list to one of the items in the existing List.
     *
     * @param head Item to set as the head of the list.
     * @throws IllegalArgumentException if the element to be set as the head is not found in the list.
     */
    public void setHead(E head) {
        if (!contains(head)) { // Ensure the element exists in the list.
            throw new IllegalArgumentException("Head element not found in list.");
        }
        this.head = head;
    }

    /**
     * An alternative method to {@link #get} which identifies the item positioned at a specified index `i`
     * starting from the current {@link #head}.
     *
     * @param i Index of the requested item relative to the head.
     * @return Item at the specified index.
     */
    public E circleGet(int i) {
        int index = super.indexOf(head); // Get the index of the head element.
        int itemIndex = index + i; // Calculate the target index by moving `i` positions from the head.
        if (itemIndex >= this.size()) {
            return super.get(itemIndex - this.size()); // Wrap around if index exceeds list size.
        }
        return super.get(itemIndex);
    }

    /**
     * Creates a new iterator for use in a for-each loop.
     * This iterator loops through the circular list, starting from the head until it reaches the last element.
     *
     * @return Iterator representing a CircularList.
     */
    @Override
    public Iterator<E> iterator() {
        return new CircularIterator();
    }

    /**
     * Class defining an iterator to loop through the circular list.
     * The iterator starts from the head element and continues in a circular manner until all elements are covered.
     */
    private class CircularIterator implements Iterator<E> {
        private int START; // The starting index of iteration (initial head).
        private int currentIndex; // The current index in the iteration.
        private boolean firstIteration; // Flag to check if it is the first iteration.

        /**
         * Creates a CircularIterator, starting at the {@link #head}.
         * If the list is empty, it throws an exception.
         *
         * @throws NoSuchElementException if attempting to create an iterator for an empty list.
         */
        public CircularIterator() {
            if (size() == 0) { // Ensure the list is not empty.
                throw new NoSuchElementException("Circular list is empty.");
            }
            START = indexOf(head); // Set the starting index to the index of the head element.
            currentIndex = START; // Initialize current index to the start.
            firstIteration = true; // Mark this as the first iteration.
        }

        /**
         * Checks if there is a next element in the iteration.
         *
         * @return True if there is a next element; otherwise false.
         */
        @Override
        public boolean hasNext() {
            return firstIteration || currentIndex != START; // Continue until we complete one full cycle.
        }

        /**
         * Returns the next element in the iteration.
         * Advances the index and wraps around if needed.
         *
         * @return The next element in the circular list.
         * @throws NoSuchElementException if no more elements are available in the iterator.
         */
        @Override
        public E next() {
            if (!hasNext()) { // If no more elements, throw an exception.
                throw new NoSuchElementException("No more elements in the iterator.");
            }
            E element = get(currentIndex); // Get the current element.
            currentIndex = (currentIndex + 1) % size(); // Advance to the next index, wrap around if necessary.
            firstIteration = false; // Mark that we are past the first iteration.
            return element;
        }
    }
}

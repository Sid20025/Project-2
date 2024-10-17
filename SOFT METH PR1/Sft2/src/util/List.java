/**
 *The List class is a custom implementation of a dynamic array used to store and manage
 *   appointments, visits, or other objects. It includes methods for adding, removing,
 *   and accessing elements, as well as sorting and resizing the array as needed.
 *   This class functions as a core data structure for managing collections of items in
 *   the scheduling system.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package util;

import java.util.Iterator;

public class List<E> implements Iterable<E> {
    // Instance variables
    private E[] objects;
    private int size;

    // Default constructor with an initial capacity of 4
    @SuppressWarnings("unchecked")
    public List() {
        this.objects = (E[]) new Object[4];
        this.size = 0;
    }

    // Method to get the size of the list
    public int size() {
        return size;
    }

    // Method to check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Find the index of an element, returns -1 if not found
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // Helper method to grow the array when it is full
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + 4];
        for (int i = 0; i < size; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    // Method to check if the list contains a specific element
    public boolean contains(E e) {
        return find(e) != -1;
    }

    // Add a new element to the list
    public void add(E e) {
        if (size >= objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    // Remove an element from the list
    public void remove(E e) {
        if (this.contains(e)) {
            int index = find(e);
            if (index == -1) return;  // If the element is not found, do nothing

            for (int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[size - 1] = null;  // Clear the last element
            size--;
        }
    }

    // Get the element at a specific index
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return objects[index];
    }

    // Set the element at a specific index
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        objects[index] = e;
    }

    // Return the index of an element, or -1 if not found
    public int indexOf(E e) {
        return find(e);
    }

    // Iterator implementation to iterate over the list
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    // Private class for iterating over the list
    private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        // Check if there is a next element
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        // Return the next element in the list
        @Override
        public E next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the list.");
            }
            return objects[currentIndex++];
        }
    }
    @Override
    public String toString() {
        String list = "";
        for (int i = 0; i < size - 1; i++) {
            list += objects[i] + "\n";
        }
        list += objects[size - 1];
        return list;
    }
}

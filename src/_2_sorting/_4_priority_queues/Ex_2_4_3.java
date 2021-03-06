package _2_sorting._4_priority_queues;

import edu.princeton.cs.algs4.StdOut;
import _1_fundamentals._3_bags_queues_and_stacks.LinkedList;
import _1_fundamentals._3_bags_queues_and_stacks.Node;

import java.util.Arrays;

/**
 * Provide priority-queue implementations that support insert and remove the
 * maximum, one for each of the following underlying data structures: unordered array,
 * ordered array, unordered linked list, and linked list. Give a table of the worst-case
 * bounds for each operation for each of your four implementations.
 */
public class Ex_2_4_3 {

    public static void main(String[] args) {

        StdOut.println(UnorderedArrayPQ.class.getSimpleName() + ":");
        ArrayPQ<Integer> unorderedArrayPQ = new UnorderedArrayPQ<>(5);
        unorderedArrayPQ.insert(8);
        unorderedArrayPQ.insert(3);
        unorderedArrayPQ.insert(6);
        unorderedArrayPQ.insert(2);
        unorderedArrayPQ.insert(7);
        while (!unorderedArrayPQ.isEmpty()) {
            StdOut.print(unorderedArrayPQ.removeMax() + " "); // 8 7 6 3 2
        }

        StdOut.println("\n" + OrderedArrayPQ.class.getSimpleName() + ":");
        ArrayPQ<Integer> orderedArrayPQ = new OrderedArrayPQ<>(5);
        orderedArrayPQ.insert(8);
        orderedArrayPQ.insert(3);
        orderedArrayPQ.insert(6);
        orderedArrayPQ.insert(2);
        orderedArrayPQ.insert(7);
        while (!orderedArrayPQ.isEmpty()) {
            StdOut.print(orderedArrayPQ.removeMax() + " ");
        }

        StdOut.println("\n" + UnorderedLinkedList.class.getSimpleName() + ":");
        LinkedListPQ<Integer> unorderedLinkedList = new UnorderedLinkedList<>(5);
        unorderedLinkedList.insert(8);
        unorderedLinkedList.insert(3);
        unorderedLinkedList.insert(6);
        unorderedLinkedList.insert(2);
        unorderedLinkedList.insert(7);
        while (!unorderedLinkedList.isEmpty()) {
            StdOut.print(unorderedLinkedList.removeMax() + " ");
        }

        StdOut.println("\n" + OrderedLinkedList.class.getSimpleName() + ":");
        LinkedListPQ<Integer> orderedLinkedList = new OrderedLinkedList<>(5);
        orderedLinkedList.insert(8);
        orderedLinkedList.insert(3);
        orderedLinkedList.insert(6);
        orderedLinkedList.insert(2);
        orderedLinkedList.insert(7);
        while (!orderedLinkedList.isEmpty()) {
            StdOut.print(orderedLinkedList.removeMax() + " ");
        }

    }

    static class OrderedLinkedList<Key extends Comparable<Key>> extends LinkedListPQ<Key> {

        OrderedLinkedList(int max) {
            super(max);
        }

        /**
         * O(n)
         */
        void insert(Key k) {
              if (!isFull()) {
                Node<Key> newNode = new Node<>(k);
                if (list.isEmpty()) {
                    list.first = newNode;
                    list.last = newNode;
                } else {
                    if (less(list.last.elem, k)) {
                        list.last.next = newNode;
                        list.last = newNode;
                    } else {
                        Node<Key> curr = list.first;
                        Node<Key> prev = null;
                        while (curr != null) {
                            if (less(k, curr.elem)) {
                                newNode.next = curr;
                                if (prev == null)
                                    list.first = newNode;
                                else {
                                    prev.next = newNode;
                                }
                                break;
                            }
                            prev = curr;
                            curr = curr.next;
                        }
                    }
                }
                count++;
            }
        }

        /**
         * O(n)
         */
        Key removeMax() {
            if (isEmpty()) {
                return null;
            } else if (list.first == list.last) {
                Key max = list.first.elem;
                list.first = null;
                list.last = null;
                return max;
            } else {
                Node<Key> curr = list.first;
                while (curr.next != list.last) {
                    curr = curr.next;
                }
                Key max = list.last.elem;
                list.last = curr;
                list.last.next = null;
                return max;
            }
        }
    }

    static class UnorderedLinkedList<Key extends Comparable<Key>> extends LinkedListPQ<Key> {

        UnorderedLinkedList(int max) {
            super(max);
        }

        /**
         * O(1)
         */
        void insert(Key key) {
            if (!isFull()) {
                list.add(key);
                count++;
            }
        }

        /**
         * O(n)
         */
        Key removeMax() {
            if (list.isEmpty()) {
                return null;
            } else {
                Node<Key> prev = null;
                Node<Key> curr = list.first;
                Key max = curr.elem;
                while (curr.next != null) {
                    if (less(max, curr.next.elem)) {
                        prev = curr;
                        max = curr.next.elem;
                    }
                    curr = curr.next;
                }
                if (prev == null) {
                    list.first = list.first.next;
                } else {
                    prev.next = prev.next.next;
                }
                count--;
                return max;
            }
        }

    }

    static abstract class LinkedListPQ<Key extends Comparable<Key>> {
        LinkedList<Key> list;
        int N;
        int count;

        LinkedListPQ(int max) {
            assert (max > 0);
            count = 0;
            N = max;
            list = new LinkedList<>();
        }

        abstract void insert(Key key);

        abstract Key removeMax();

        boolean isFull() {
            return count == N;
        }

        boolean isEmpty() {
            return list.isEmpty();
        }

        boolean less(Key k1, Key k2) {
            return k1.compareTo(k2) < 0;
        }

        int size() {
            return count;
        }
    }

    // Similar to Selection sort
    static class UnorderedArrayPQ<Key extends Comparable<Key>> extends ArrayPQ<Key> {

        UnorderedArrayPQ(int max) {
            super(max);
        }

        /**
         * O(1)
         */
        void insert(Key key) {
            if (!isFull()) {
                pq[count++] = key;
            }
        }

        /**
         * O(n)
         */
        Key removeMax() {
            Key max = null;
            if (!isEmpty()) {
                int maxIndex = findMaxIndex();
                max = pq[maxIndex];
                pq[maxIndex] = null;
                compact();
            }
            return max;
        }

        private int findMaxIndex() {
            int max = 0;
            for (int i = 1; i < pq.length; i++) {
                if (pq[i] != null && less(max, i)) {
                    max = i;
                }
            }
            return max;
        }

        private void compact() {
            Key[] aux = Arrays.copyOf(pq, pq.length);
            int j = 0;
            for (int i = 0; i < pq.length; i++) {
                if (pq[i] != null) {
                    aux[j] = pq[i];
                    if (i > j) {
                        aux[i] = null;
                    }
                    j++;
                }
            }
            pq = aux;
            count = j;
        }

    }

    // Similar to Insertion sort
    static class OrderedArrayPQ<Key extends Comparable<Key>> extends ArrayPQ<Key> {

        OrderedArrayPQ(int max) {
            super(max);
        }

        /**
         * O(n)
         */
        void insert(Key k) {
            if (!isFull()) {
                int i = count;
                count++;
                pq[i] = k;
                while (i > 0 && less(i, i - 1)) {
                    swap(i - 1, i);
                    i--;
                }
            }
        }

        /**
         * O(1)
         */
        Key removeMax() {
            if (isEmpty()) {
                return null;
            } else {
                return pq[--count];
            }
        }

    }


    static abstract class ArrayPQ<Key extends Comparable<Key>> {
        Key[] pq;
        int count;

        ArrayPQ(int max) {
            assert (max > 0);
            count = 0;
            pq = (Key[]) new Comparable[max];
        }

        abstract void insert(Key k);

        abstract Key removeMax();

        void swap(int i, int j) {
            Key tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }

        boolean isFull() {
            return count == pq.length;
        }

        boolean isEmpty() {
            return count == 0;
        }

        boolean less(int i, int j) {
            return pq[i].compareTo(pq[j]) < 0;
        }

        int size() {
            return count;
        }

    }
}

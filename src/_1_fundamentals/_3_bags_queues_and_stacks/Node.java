package _1_fundamentals._3_bags_queues_and_stacks;

public class Node<E> {
    public final E elem;
    public Node<E> next;

    public Node(E elem) {
        this.elem = elem;
        this.next = null;
    }

    @Override
    public String toString() {
        if(this.next == null)
            return elem + "";
        return elem + " -> " + next;
    }

}

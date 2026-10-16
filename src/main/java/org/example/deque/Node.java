package org.example.deque;

// Узел стека: хранит число, минимум и ссылку на следующий узел.
public class Node {
    int value;
    int min;
    Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;

        if (this.next == null) {
            this.min = value;
        } else {
            this.min = Math.min(value, next.min);
        }
    }
}

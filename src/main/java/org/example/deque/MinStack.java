package org.example.deque;

// Стек с получением минимума за O(1).
// Последний добавленный элемент извлекается первым.
public class MinStack {
    private Node top;

    public void push(int value) {
        this.top = new Node(value, top);
    }

    public int pop() {
        int topValue = top.value;
        top = top.next;
        return topValue;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int getMin() {
        return top.min;
    }
}
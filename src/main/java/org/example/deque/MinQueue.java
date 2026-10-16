package org.example.deque;

// Очередь на двух собственных стеках.
// Первый добавленный элемент извлекается первым.
public class MinQueue {
    private MinStack in = new MinStack();
    private MinStack out = new MinStack();

    public void add(int value) {
        in.push(value);
    }

    private void transfer() {
        // Перекладываем все элементы из входного стека в выходной.
        // Порядок переворачивается: самый ранний элемент
        // оказывается на вершине выходного стека.
        while (!in.isEmpty()) {
            int value = in.pop();
            out.push(value);
        }
    }

    public int remove() {
        if (out.isEmpty()) {
            transfer();
        }
        return out.pop();
    }

    public int getMin() {
        if (in.isEmpty()) {
            return out.getMin();
        } else if (out.isEmpty()) {
            return in.getMin();
            // Элементы очереди распределены между двумя стеками.
            // Общий минимум — меньшее из минимумов этих стеков.
        } else {
            return Math.min(in.getMin(), out.getMin());
        }
    }
}

package org.example;

import java.util.Scanner;

public class Security {
    public static void main(String[] args) {
        // Создаём Scanner для чтения входных данных из консоли.
        Scanner scanner = new Scanner(System.in);
        // Читаем количество независимых наборов охранников.
        int amountOfTests = scanner.nextInt();

        // Каждый набор обрабатываем отдельно.
        for (int test = 0; test < amountOfTests; test++) {
            int guards = scanner.nextInt();
            int[] intervals = new int[10001];
            int[][] time = readIntervals(guards, scanner);
            fillIntervals(intervals, time);

            // Набор подходит, если одновременно выполнены два условия:
            // 1. Все промежутки охраняются.
            // 2. Каждый охранник необходим.
            // Оператор && требует выполнения обоих условий.
            // Если первое условие ложно, вторую проверку Java не вызывает.
            if (isFullyCovered(intervals) && areAllNecessary(intervals, time)) {
                System.out.println("Accepted");
            } else {
                System.out.println("Wrong Answer");
            }
        }

    }

    // Читает интервалы дежурства одного набора охранников.
    private static int[][] readIntervals(int guards, Scanner scanner) {
        int[][] workingTime = new int[guards][2];
        for (int i = 0; i < guards; i++) {
            int arrival = scanner.nextInt();
            int departure = scanner.nextInt();
            workingTime[i][0] = arrival;
            workingTime[i][1] = departure;
        }

        return workingTime;
    }

    // Подсчитывает количество дежурящих в каждом промежутке суток.
    private static void fillIntervals(int[] intervals, int[][] time) {
        for (int[] ints : time) {
            int firstStart = ints[0];
            int firstEnd = ints[1];
            for (int j = firstStart + 1; j <= firstEnd; j++) {
                intervals[j]++;
            }
        }
    }

    // Проверяет, охраняется ли объект во всех промежутках суток.
    private static boolean isFullyCovered(int[] intervals) {
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i] == 0) {
                return false;
            }
        }
        return true;
    }

    // Проверяет, необходим ли каждый охранник из набора.
    private static boolean areAllNecessary(int[] intervals, int[][] time) {
        for (int i = 0; i < time.length; i++) {
            int arrival = time[i][0];
            int departure = time[i][1];

            if (!isNecessary(intervals, arrival, departure)) {
                return false;
            }
        }
        return true;
    }

    // Проверяет необходимость одного охранника.
    private static boolean isNecessary(int[] intervals, int arrival, int departure) {
        for (int i = arrival + 1; i <= departure; i++) {
            if (intervals[i] == 1) {
                return true;
            }
        }
        return false;
    }
}

package org.example;

import java.util.Scanner;

//Задача 18. Музей.
//Формат входных данных:
//   В первой строке входного файла INPUT.TXT записано натуральное число N (N <
//   105) – количество зафиксированных посетителей в музее в течении суток. Далее, идут N
//   строк с информацией о времени визитов посетителей: в каждой строке располагается
//   отрезок времени посещения в формате «ЧЧ:ММ ЧЧ:ММ» (00:00 ≤ ЧЧ:ММ ≤ 23:59).
//Формат выходных данных:
//   В единственную строку выходного файла OUTPUT.TXT нужно вывести одно целое
//   число — максимальное количество посетителей, одновременно находящихся в музее.

public class Museum {
    public static void main(String[] args) {
        // Создаём Scanner для чтения входных данных из консоли.
        Scanner scanner = new Scanner(System.in);
        // Массив из 1440 элементов (количество минут в сутках) будем заполнять значениями
        // для определения макс. числа посетителей
        int[] visitors = new int[1440];
        int amount = scanner.nextInt();
        int[][] time = readTime(amount, scanner);
        fillVisitors(visitors, time);
        int maxVisitors = findMax(visitors);
        System.out.println(maxVisitors);

    }

    // Ищем максимальное число посетителей
    private static int findMax(int[] visitors) {
        int max = 0;
        for(int count: visitors) {
            if(count > max) {
                max = count;
            }
        }
        return max;
    }

    // Заполняем массив для определения промежутков прибывания посетителей в музее
    private static void fillVisitors(int[] visitors, int[][] time) {
        for (int[] ints : time) {
            int firstStart = ints[0];
            int firstEnd = ints[1];
            for (int j = firstStart; j <= firstEnd; j++) {
                visitors[j]++;
            }
        }
    }

    // Считываем время прихода и ухода
    private static int[][] readTime(int amount, Scanner scanner) {
        int count = 0;
        int[][] times = new int[amount][2];
        while(count < amount) {
            String arrival = scanner.next();
            int start = toMinutes(arrival);
            String departure = scanner.next();
            int end = toMinutes(departure);
            times[count][0] = start;
            times[count][1] = end;
            count++;
        }
        return times;
    }

    // Переводим часы и минуты к минутам
    private static int toMinutes(String time) {
        int minutesInHour = 60;
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        return hours * minutesInHour + Integer.parseInt(parts[1]);
    }
}

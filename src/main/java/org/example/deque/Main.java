package org.example.deque;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(System.out));

        MinQueue queue = new MinQueue();
        int m = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < m; i++) {
            String line = reader.readLine().trim();
            char command = line.charAt(0);

            switch (command) {
                case '+':
                    // Убираем знак команды и пробелы, получаем число.
                    int value = Integer.parseInt(line.substring(1).trim());
                    queue.add(value);
                    break;

                case '-':
                    // Удаляем первый элемент. Выводить его не требуется.
                    queue.remove();
                    break;

                case '?':
                    // Преобразуем число в строку и выводим отдельной строкой.
                    writer.write(Integer.toString(queue.getMin()));
                    writer.newLine();
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Неизвестная команда: " + command
                    );
            }
        }

        writer.flush(); // Отправляем накопленный вывод в консоль.
    }
}

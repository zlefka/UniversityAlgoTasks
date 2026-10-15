package org.example;

import java.util.Scanner;

//Задача 6. Вычисление полинома.
//Формат входных данных:
//        Первая строка файла содержит три числа – степень полинома 2 ≤ N ≤ 100000,
//        количеств вычисляемых значений аргумента 1 ≤ M ≤ 10000 и модуль 10 ≤ MOD ≤ 109
//        Следующие N+1 строк содержат значения коэффициентов полинома 0 ≤ ai ≤ 109
//        В очередных M строках содержатся значения аргументов 0 ≤ xi ≤ 109
// Формат выходных данных:
//        Выходной файл должен состоять из ровно M строк – значений данного полинома
//        при заданных значениях аргументов по модулю MOD.


public class Polynomial {
    public static void main(String[] args){
        // Создаём Scanner для чтения входных данных из консоли.
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // степень полинома
        int m = scanner.nextInt(); // количиство иксов
        int mod = scanner.nextInt(); // модуль

        // У полинома степени n есть n + 1 коэффициент.
        // Сохраняем их в порядке от старшей степени к свободному члену.
        int[] coefficients = new int[n + 1];
        for(int i = 0; i < coefficients.length; i++) {
            coefficients[i] = scanner.nextInt();
        }

        // Читаем значения x, для которых нужно вычислить полином.
        int[] arguments = new int[m];
        for(int i = 0; i < arguments.length; i++) {
            arguments[i] = scanner.nextInt();
        }

        // Вычисляем полином отдельно для каждого x.
        for(int i = 0; i < arguments.length; i++) {
            // Старший коэффициент
            long res = coefficients[0] % mod;

            // Используем схему Горнера: умножаем накопленный
            // результат на x и прибавляем следующий коэффициент.
            for(int j = 1; j < coefficients.length; j++) {
                res = (res * arguments[i] + coefficients[j]) % mod;
            }
            System.out.println(res);
        }
    }
}

package org.example.billionaires;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.next());
        NameIndex peopleIndexes = new NameIndex(n);
        Billionaire[] people = new Billionaire[n];
        // Изначально не более n городов; каждый из 50000 переездов может добавить один.
        CityRanking ranking = new CityRanking(n + 50000);

        // Читаем начальное положение всех миллиардеров.
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            String city = scanner.next();
            long wealth = Long.parseLong(scanner.next());

            Billionaire person = new Billionaire(city, wealth, name);
            people[peopleIndexes.getOrAdd(name)] = person;
            ranking.changeWealth(city, wealth);
        }

        int m = Integer.parseInt(scanner.next()); // Количество дней наблюдения.
        int k = Integer.parseInt(scanner.next()); // Количество переездов.

        // Все дни до этой границы включительно уже учтены.
        // Изначально ещё не учтён ни один день.
        int previousDay = 0;

        // Переезды поступают в порядке возрастания номера дня.
        for (int i = 0; i < k; i++) {
            int day = Integer.parseInt(scanner.next());
            String name = scanner.next();
            String destination = scanner.next();

            // Переезд происходит вечером, поэтому в день day
            // человек ещё учитывается в старом городе.
            // Начисляем дни ДО изменения сумм.
            if (day > previousDay) {
                // Между переездами суммы не меняются:
                // начисляем сразу весь отрезок дней.
                ranking.addDays(day - previousDay);
                previousDay = day;
            }

            // Если несколько переездов происходят в один день,
            // условие выше выполняется только для первого из них.
            // Промежуточные состояния рейтинга дни не получают.

            // Находим человека по имени и получаем его данные.
            Billionaire person = people[peopleIndexes.get(name)];
            String oldCity = person.getCity();
            long wealth = person.getWealth();

            // Вычитаем состояние из старого города.
            ranking.changeWealth(oldCity, -wealth);

            // Прибавляем состояние к новому городу.
            ranking.changeWealth(destination, wealth);

            // Сохраняем новый город для следующих переездов человека.
            person.setCity(destination);
        }

        // Учитываем дни после последнего переезда до конца периода.
        // Если k = 0, previousDay равен 0: начисляются все m дней.
        ranking.addDays(m - previousDay);
        ranking.printResult();
    }
}

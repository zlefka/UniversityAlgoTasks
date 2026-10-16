package org.example.billionaires;

import java.util.*;

// Задача 22. Миллиардеры
// Формат входных данных:
//        В первой строке записано число n — количество миллиардеров (1 ≤ n ≤ 10000).
//        Каждая из следующих n строк содержит данные на определённого человека: его имя,
//        название города, где он находился в первый день данного периода, и размер состояния. В
//        следующей строке записаны два числа: m — количество дней, о которых есть данные (1
//                                                                                                 ≤ m ≤ 50000), k — количество зарегистрированных перемещений миллиардеров (0 ≤ k ≤
//        50000). Следующие k строк содержат список перемещений в формате: номер дня (от 1
//        до m − 1), имя человека, название города назначения. Вы можете считать, что миллиардеры
//        путешествуют не чаще одного раза в день, и что они отбывают поздно вечером и прибывают
//        в город назначения рано утром следующего дня. Список упорядочен по возрастанию
//        номера дня. Все имена и названия городов состоят не более чем из 20 латинских букв,
//        регистр букв имеет значение. Состояния миллиардеров лежат в пределах от 1 до 100
//        миллиардов.
// Формат выходных данных:
//        В каждой строке должно содержаться название города и, через пробел, количество
//        дней, в течение которых этот город лидировал по общему состоянию миллиардеров,
//        находящихся в нём. Если таких дней не было, пропустите этот город. Города должны быть
//        отсортированы по алфавиту (используйте обычный порядок символов: ABC...Zabc...z).

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Имя → объект человека.
        // Позволяет быстро найти миллиардера при переезде.
        Map<String, Billionaire> people = new HashMap<>();

        // Город → общая сумма состояний находящихся в нём людей.
        // Long нужен, поскольку суммы не помещаются в int.
        Map<String, Long> cityWealth = new HashMap<>();

        // Сумма денег → множество городов с такой суммой.
        // TreeMap сортирует суммы, поэтому можно быстро найти максимальную.
        // Set позволяет хранить несколько городов с одинаковой суммой.
        TreeMap<Long, Set<String>> ranking = new TreeMap<>();

        int n = scanner.nextInt();

        // Читаем начальное положение всех миллиардеров.
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            String city = scanner.next();
            long wealth = scanner.nextLong();

            Billionaire person = new Billionaire(city, wealth, name);
            people.put(name, person);

            // Если город встретился впервые, его начальная сумма равна 0.
            long currentWealth = cityWealth.getOrDefault(city, 0L);

            // Добавляем состояние человека к сумме его города.
            cityWealth.put(city, currentWealth + wealth);
        }

        // Формируем начальный рейтинг после подсчёта всех сумм.
        // entry — одна пара «город → сумма» из cityWealth.
        for (Map.Entry<String, Long> entry : cityWealth.entrySet()) {
            String city = entry.getKey();
            long wealth = entry.getValue();

            // Создаём группу для суммы, если её ещё нет.
            // Существующая группа при этом не перезаписывается.
            ranking.putIfAbsent(wealth, new HashSet<>());

            // Добавляем город в группу с соответствующей суммой.
            ranking.get(wealth).add(city);
        }

        int m = scanner.nextInt(); // Количество дней наблюдения.
        int k = scanner.nextInt(); // Количество переездов.

        // Город → количество дней его лидерства.
        // TreeMap хранит названия в требуемом алфавитном порядке.
        Map<String, Integer> result = new TreeMap<>();

        // Все дни до этой границы включительно уже учтены.
        // Изначально ещё не учтён ни один день.
        int previousDay = 0;

        // Переезды поступают в порядке возрастания номера дня.
        for (int i = 0; i < k; i++) {
            int day = scanner.nextInt();
            String name = scanner.next();
            String destination = scanner.next();

            // Переезд происходит вечером, поэтому в день day
            // человек ещё учитывается в старом городе.
            // Начисляем дни ДО изменения сумм.
            if (day > previousDay) {
                // Между переездами суммы не меняются:
                // начисляем сразу весь отрезок дней.
                addDays(ranking, result, day - previousDay);
                previousDay = day;
            }

            // Если несколько переездов происходят в один день,
            // условие выше выполняется только для первого из них.
            // Промежуточные состояния рейтинга дни не получают.

            // Находим человека по имени и получаем его данные.
            Billionaire person = people.get(name);
            String oldCity = person.getCity();
            long wealth = person.getWealth();

            // Вычитаем состояние из старого города.
            changeCityWealth(oldCity, -wealth, cityWealth, ranking);

            // Прибавляем состояние к новому городу.
            changeCityWealth(destination, wealth, cityWealth, ranking);

            // Сохраняем новый город для следующих переездов человека.
            person.setCity(destination);
        }

        // Учитываем дни после последнего переезда до конца периода.
        // Если k = 0, previousDay равен 0: начисляются все m дней.
        addDays(ranking, result, m - previousDay);

        // В result попадают только города с положительным числом дней.
        // Обход TreeMap выдаёт их по алфавиту.
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    // Изменяет сумму одного города и обновляет его группу в рейтинге.
    // change отрицательное при отъезде и положительное при приезде.
    private static void changeCityWealth(
            String city,
            long change,
            Map<String, Long> cityWealth,
            TreeMap<Long, Set<String>> ranking) {

        // Для нового города считаем прежнюю сумму равной нулю.
        long oldWealth = cityWealth.getOrDefault(city, 0L);

        // Получаем группу городов с прежней суммой.
        Set<String> cities = ranking.get(oldWealth);

        // Группы может не быть, например при появлении нового города.
        if (cities != null) {
            // Удаляем только нужный город, остальные остаются в группе.
            cities.remove(city);

            // Пустую группу удаляем, чтобы она не участвовала
            // в поиске максимальной суммы.
            if (cities.isEmpty()) {
                ranking.remove(oldWealth);
            }
        }

        // Рассчитываем и сохраняем новую сумму.
        long newWealth = oldWealth + change;
        cityWealth.put(city, newWealth);

        // Создаём группу новой суммы, если необходимо,
        // и помещаем в неё город.
        ranking.putIfAbsent(newWealth, new HashSet<>());
        ranking.get(newWealth).add(city);
    }

    // Возвращает название единственного лидера.
    // Если первое место делят несколько городов, возвращает null.
    private static String findLeader(TreeMap<Long, Set<String>> ranking) {
        // lastEntry() получает пару с максимальным ключом — суммой.
        // getValue() получает множество городов с этой суммой.
        Set<String> leaders = ranking.lastEntry().getValue();

        if (leaders.size() == 1) {
            // У Set нет обращения по индексу.
            // Через итератор получаем единственный элемент множества.
            return leaders.iterator().next();
        }

        // Единственного лидера нет.
        return null;
    }

    // Начисляет текущему лидеру заданное количество дней.
    private static void addDays(
            TreeMap<Long, Set<String>> ranking,
            Map<String, Integer> result,
            int days) {

        String leader = findLeader(ranking);

        // Начисляем только положительное количество дней
        // и только при наличии единственного лидера.
        if (leader != null && days > 0) {
            // При первом начислении у города было 0 дней.
            int currentDays = result.getOrDefault(leader, 0);

            // Прибавляем новый отрезок к ранее накопленным дням.
            result.put(leader, currentDays + days);
        }
    }
}
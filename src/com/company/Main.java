package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    // Файл из которого считываем данные
    public static final File FILE_INPUT = new File("input.txt");
    // Файл в который записываем результат
    public static final File FILE_OUTPUT = new File("output.txt");

    // Результат для файла output.txt (количество запросов в распределенную систему)
    private static int countQueriesForOutputFile;

    // Количество запросов в файле
    private static int countQueriesFromInputFile;
    // Массив запросов
    private static int[] queriesMas;

    // Объем кеш
    private static int countCacheQuery;
    // Массив кеш
    private static int[] casheMas;

    // Map запроса с максимальным номером строки (в файле input.txt)
    private static Map<Integer, Integer> mapQuery = new HashMap<>();

    public static void main(String[] args) {

        try {
            // Создаем объект FileReader для объекта File
            FileReader fr = new FileReader(FILE_INPUT);
            // Создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);

            // Считаем сначала первую строку
            String line = reader.readLine();
            readFirstLineFromInput(line);

            // Создаем массивы
            queriesMas = new int[countQueriesFromInputFile];
            casheMas = new int[countCacheQuery];

            int i = 0;
            line = reader.readLine();
            while (line != null) {
                // Добавляем элемент в массив
                int num = Integer.parseInt(line);
                queriesMas[i] = num;

                // Также добавляем его в map
                mapQuery.put(num,i);

                // Считываем остальные строки в цикле
                line = reader.readLine();

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int casheMasIter = 0;
        // Обрабатываем по порядку поступающие запросы
        for (int i = 0; i < queriesMas.length; i++) {

            // Добавляем элемент в массив кеша, если там элемента нет
            if (!checkQueryInCache(casheMas, queriesMas[i])) {
                if (casheMas.length > casheMasIter) {
                    casheMas[casheMasIter] = queriesMas[i];
                    casheMasIter++;
                } else {
                    // Очищаем из кеш неиспользуемый запрос и пробуем добавить наш новый запрос
                      optimizeQueryMas(i);
                }
                // Отмечаем очередное обращение в распределенную систему
                countQueriesForOutputFile++;
            }
        }

        // Записываем результат в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_OUTPUT))){
            writer.write(String.valueOf(countQueriesForOutputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Получаем информацию по объему кеш и количеству запросов
     * @param str Первая строка из файла input.txt
     */
    public static void readFirstLineFromInput(String str) {
        String[] firstLine = str.split(" ");
        countCacheQuery = Integer.parseInt(firstLine[0]);
        countQueriesFromInputFile = Integer.parseInt(firstLine[1]);
    }

    /**
     * Проверяем наличие элемента в массиве кеш
     * @param casheMas Массив кеш
     * @param element Элемент, который проверяем
     * @return True если элемент имеется в массиве
     */
    public static boolean checkQueryInCache(int[] casheMas, int element) {
        for (int num : casheMas) {
            if (num == element) return true;
        }
        return false;
    }

    /**
     * Оптимизируем массив кеша, удаляем неиспользуемый запрос и добавляем новый
     * @param currentNum текущий номер, который считывается из массива запросов
     */
    public static void optimizeQueryMas(int currentNum) {
        for (int i = 0; i < casheMas.length; i++) {
            if (mapQuery.get(casheMas[i]) <= currentNum) {
                casheMas[i] = queriesMas[i];
            }
        }
    }
}

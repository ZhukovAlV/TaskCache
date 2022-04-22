package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {

    // Количество элементов в массиве
    private static final int COUNT_ELEMENTS_MAS = 100_000;

    // Количество элементов в кеш
    private static final int SIZE_CACHE_MAS = 5;

    // Диапазон случайных чисел
    private static final double RANGE = Math.pow(2,63) - 1;

    public static void main(String[] args) {

        // Создаем массив случайных чисел
        Random random = new Random();
        long[] array = new long[COUNT_ELEMENTS_MAS];
        for (int i = 0; i < array.length; i++) {
            // Нуля в нашем массиве не предполагается
            array[i] = (long)random.nextDouble(RANGE) + 1;
            System.out.println(array[i]);
        }

        // Записываем это все в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.FILE_INPUT))){
            writer.write(String.join(" ",
                    String.valueOf(SIZE_CACHE_MAS),
                    String.valueOf(COUNT_ELEMENTS_MAS),
                    "\n"));

            for (long num : array) {
                writer.append(String.valueOf(num)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Проверяем наш массив на время выполнения
        long start = System.currentTimeMillis();

        Main main = new Main();
        main.main(new String[]{});

        long finish = System.currentTimeMillis();
        double elapsed = (finish - start) / 1000D;
        System.out.println("Прошло времени, с: " + elapsed);

        // Смотрим сколько памяти съедает наша программа
        double usedBytes = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1_048_576D;
        System.out.println("Использовано памяти, Мб: " + usedBytes);
    }
}

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
    private static final int RANGE = 100;

    public static void main(String[] args) {

        // Создаем массив случайных чисел
        Random random = new Random();
        int[] array = new int[COUNT_ELEMENTS_MAS];
        for (int i = 0; i < array.length; i++) {
            // Нуля в нашем массиве не предполагается
            array[i] = random.nextInt(RANGE) + 1;
            System.out.println(array[i]);
        }

        // Записываем это все в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.FILE_INPUT))){
            writer.write(String.join(" ",
                    String.valueOf(SIZE_CACHE_MAS),
                    String.valueOf(COUNT_ELEMENTS_MAS),
                    "\n"));

            for (int num : array) {
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
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}

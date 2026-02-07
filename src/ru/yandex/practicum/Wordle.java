package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        // Создаем лог-файл через try-with-resources
        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log", StandardCharsets.UTF_8, true))) {
            runGame(log);
        } catch (IOException e) {
            System.err.println("Критическая ошибка: не удалось создать лог-файл.");
        }
    }

    private static void runGame(PrintWriter log) {
        Scanner scanner = new Scanner(System.in);
        try {
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.load("words_ru.txt", log);
            WordleGame game = new WordleGame(dictionary, log);

            System.out.println("Добро пожаловать в Wordle! У вас 6 попыток угадать слово из 5 букв.");
            System.out.println("Нажмите Enter в пустой строке для подсказки.");

            while (game.hasSteps()) {
                System.out.println("\n--- Попыток осталось: " + game.getRemainingSteps() + " ---");
                System.out.print("Введите слово > ");

                String input = scanner.nextLine().trim();
                String wordToProcess;

                if (input.isEmpty()) {
                    wordToProcess = game.getHint();
                    System.out.println("Подсказка: " + wordToProcess);
                    log.println("Пользователь нажал Enter. Автоматически выбрано слово: " + wordToProcess);
                } else {
                    wordToProcess = input;
                }

                try {
                    // Теперь мы вызываем makeMove для слова (будь то ввод или подсказка)
                    String feedback = game.makeMove(wordToProcess);

                    System.out.println("Результат: " + feedback);
                    log.println("Ход: " + wordToProcess + " | Результат: " + feedback);

                    if (game.isWin(feedback)) {
                        System.out.println("\nПоздравляем! Вы угадали слово!");
                        return;
                    }

                } catch (GameException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    log.println("Ошибка при вводе '" + wordToProcess + "': " + e.getMessage());
                }
            }



            System.out.println("Игра окончена. Загаданное слово было: " + game.getAnswer());

        } catch (Exception e) {
            log.println("Критический сбой программы: " + e.toString());
            e.printStackTrace(log);
            System.out.println("Произошла системная ошибка. Подробности в wordle.log");
        }
    }
}
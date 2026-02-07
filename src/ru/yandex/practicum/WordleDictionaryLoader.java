package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    public WordleDictionary load(String filename, PrintWriter log) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String normalized = line.trim().toLowerCase().replace('ё', 'е');
                if (normalized.length() == 5) {
                    words.add(normalized);
                }
            }
            log.println("Словарь успешно загружен. Количество слов: " + words.size());
        } catch (IOException e) {
            log.println("Ошибка при чтении файла словаря: " + e.getMessage());
            throw new DictionaryException("Не удалось загрузить словарь.");
        }

        if (words.isEmpty()) {
            throw new DictionaryException("Словарь пуст.");
        }
        return new WordleDictionary(words);
    }
}
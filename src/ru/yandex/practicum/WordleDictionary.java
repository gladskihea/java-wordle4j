package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }
}

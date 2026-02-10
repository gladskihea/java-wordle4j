package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    private final String answer;
    private final WordleDictionary dictionary;
    private final List<String> attempts = new ArrayList<>();
    private final List<String> feedbacks = new ArrayList<>();
    private int steps = 0;
    private final int maxSteps = 6;
    private final PrintWriter log;

    public int getRemainingSteps() {
        return maxSteps - steps;
    }

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.log = log;
        log.println("Игра начата. Загадано слово: " + answer);
    }

    public String makeMove(String input) {
        String word = input.trim().toLowerCase().replace('ё', 'е');

        if (word.length() != 5) throw new GameException("Слово должно состоять из 5 букв!");
        if (!dictionary.contains(word)) throw new WordNotFoundException();

        String feedback = checkWord(word);
        attempts.add(word);
        feedbacks.add(feedback);
        steps++;

        return feedback;
    }

    private String checkWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char current = word.charAt(i);

            int matchType;
            if (current == answer.charAt(i)) {
                matchType = 2;
            } else if (answer.contains(String.valueOf(current))) {
                matchType = 1;
            } else {
                matchType = 0;
            }

            switch (matchType) {
                case 2 -> sb.append("+");
                case 1 -> sb.append("^");
                case 0 -> sb.append("-");
            }
        }
        return sb.toString();
    }

    public String getHint() {
        log.println("Запрошена подсказка. Текущих попыток: " + steps);
        List<String> possibleWords = dictionary.getWords();

        // Фильтрация словаря на основе всех предыдущих ходов
        for (int i = 0; i < steps; i++) {
            String word = attempts.get(i);
            String feedback = feedbacks.get(i);
            possibleWords.removeIf(w -> !matchesFeedback(w, word, feedback));
        }

        if (possibleWords.isEmpty()) return dictionary.getRandomWord();
        return possibleWords.get(new Random().nextInt(possibleWords.size()));
    }

    private boolean matchesFeedback(String candidate, String word, String feedback) {
        for (int i = 0; i < 5; i++) {
            char f = feedback.charAt(i);
            char c = word.charAt(i);
            if (f == '+' && candidate.charAt(i) != c) return false;
            if (f == '-' && candidate.contains(String.valueOf(c))) return false;
            if (f == '^' && (!candidate.contains(String.valueOf(c)) || candidate.charAt(i) == c)) return false;
        }
        return true;
    }

    public boolean isWin(String feedback) {
        return feedback.equals("+++++");
    }

    public boolean hasSteps() {
        return steps < maxSteps;
    }

    public String getAnswer() {
        return answer;
    }
}
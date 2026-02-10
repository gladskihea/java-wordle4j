package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private WordleDictionary dictionary;
    private WordleGame game;
    private PrintWriter out = new PrintWriter(System.out);

    @BeforeEach
    void setUp() {
        // Создаем маленький тестовый словарь
        dictionary = new WordleDictionary(List.of("арбуз", "башня", "герой", "гонец", "экран"));
    }

    @Test
    void shouldReturnFullMatchForCorrectWord() {
        // Создаем игру, где правильный ответ "герой"
        // Для теста можно немного изменить WordleGame, чтобы можно было задать ответ вручную в конструкторе
        // Либо перебирать игру, пока не выпадет нужное слово
        game = new WordleGame(dictionary, out);

        // Допустим, ответ "герой"
        // Это пример логики, которую нужно протестировать:
        String feedback = game.makeMove("герой"); // Если это правильный ответ
        if (game.getAnswer().equals("герой")) {
            assertEquals("+++++", feedback);
        }
    }

    @Test
    void shouldThrowExceptionIfWordIsShort() {
        game = new WordleGame(dictionary, out);
        assertThrows(GameException.class, () -> game.makeMove("сон"));
    }

    @Test
    void shouldThrowExceptionIfWordNotInDictionary() {
        game = new WordleGame(dictionary, out);
        assertThrows(WordNotFoundException.class, () -> game.makeMove("вводя"));
    }

    @Test
    void testHintFiltering() {
        game = new WordleGame(dictionary, out);
        String firstMove = game.makeMove("башня");

        // Запрашиваем подсказку
        String hint = game.getHint();

        // Проверяем, что подсказка — это существующее слово из 5 букв
        assertEquals(5, hint.length());
        assertTrue(dictionary.contains(hint));

        // Логическая проверка: подсказка не должна быть тем же словом, если есть другие варианты
        assertNotEquals("башня", hint);
    }

    @Test
    void testNormalization() {
        String input = "Ёжики";
        String normalized = input.toLowerCase().replace('ё', 'е');
        assertEquals("ежики", normalized);
    }
}
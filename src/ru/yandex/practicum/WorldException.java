package ru.yandex.practicum;

// Базовое исключение для проекта
class WordleException extends RuntimeException {
  public WordleException(String message) { super(message); }
}

// Ошибки при работе с файлами/словарем
class DictionaryException extends WordleException {
  public DictionaryException(String message) { super(message); }
}

// Игровые ошибки (неверный ввод пользователя)
class GameException extends WordleException {
  public GameException(String message) { super(message); }
}

class WordNotFoundException extends GameException {
  public WordNotFoundException() { super("Слово не найдено в словаре."); }
}
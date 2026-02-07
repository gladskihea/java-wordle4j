package ru.yandex.practicum;

public class WordNotFoundException extends GameException {

  public WordNotFoundException() {
    super("Слово не найдено в словаре.");
  }
}
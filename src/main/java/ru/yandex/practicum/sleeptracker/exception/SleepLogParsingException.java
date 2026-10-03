package ru.yandex.practicum.sleeptracker.exception;

public class SleepLogParsingException extends RuntimeException {
    public SleepLogParsingException(String message) {
        super(message);
    }
}

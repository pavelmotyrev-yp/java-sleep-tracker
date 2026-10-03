package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.entity.SleepingSession;

import java.util.List;

public interface SleepAnalyzer<T> {
    String getName();

    T analyze(List<SleepingSession> sessions);
}

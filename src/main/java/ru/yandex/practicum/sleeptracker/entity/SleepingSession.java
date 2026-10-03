package ru.yandex.practicum.sleeptracker.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, SleepQuality sleepQuality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.sleepQuality = sleepQuality;
    }

    public long getDurationSleepInMinutes() {
        return Duration.between(sleepStart, sleepEnd).toMinutes();
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}

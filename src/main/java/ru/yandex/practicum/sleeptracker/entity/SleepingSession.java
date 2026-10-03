package ru.yandex.practicum.sleeptracker.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime sleepStart, SleepQuality sleepQuality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepStart;
        this.sleepQuality = sleepQuality;
    }

    public long getDurationSleepInMinutes(){
        return Duration.between(sleepStart, sleepEnd).toMinutes();
    }
}

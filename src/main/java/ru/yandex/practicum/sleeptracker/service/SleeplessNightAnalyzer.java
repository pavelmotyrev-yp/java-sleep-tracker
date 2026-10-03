package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.entity.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

public class SleeplessNightAnalyzer implements SleepAnalyzer {

    private static final LocalTime NOON = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public String getName() {
        return "Количество бессонных ночей";
    }

    @Override
    public Object analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0L;
        }

        List<SleepingSession> sortedSessions = sessions.stream()
                .sorted(Comparator.comparing(SleepingSession::getSleepStart))
                .toList();

        LocalDateTime firstSessionStart = sortedSessions.getFirst().getSleepStart();
        LocalDate firstNight = firstSessionStart.toLocalTime().isBefore(NOON)
                ? firstSessionStart.toLocalDate()
                : firstSessionStart.toLocalDate().plusDays(1);

        LocalDateTime lastSessionEnd = sortedSessions.getLast().getSleepEnd();
        LocalDate lastNight = lastSessionEnd.toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(firstNight, lastNight);

        if (daysBetween < 0) {
            return 0L;
        }

        return LongStream.rangeClosed(0, daysBetween)
                .mapToObj(firstNight::plusDays)
                .filter(night -> isNightSleepless(night, sortedSessions))
                .count();
    }

    private boolean isNightSleepless(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime thisNightStart = LocalDateTime.of(night, NIGHT_START);
        LocalDateTime thisNightEnd = LocalDateTime.of(night, NIGHT_END);
        Optional<SleepingSession> optionalResult = sessions.stream()
                .filter(session ->
                        session.getSleepStart().isBefore(thisNightStart) && session.getSleepEnd().isAfter(thisNightStart)
                                || session.getSleepStart().isBefore(thisNightEnd) && session.getSleepEnd().isAfter(thisNightEnd))
                .findFirst();
        return optionalResult.isEmpty();
    }
}

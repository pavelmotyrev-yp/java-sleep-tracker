package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.entity.Chronotype;
import ru.yandex.practicum.sleeptracker.entity.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ChronotypeAnalyzer implements SleepAnalyzer {

    private static final LocalTime NOON = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private static final LocalTime OWL_SLEEP_THRESHOLD = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_THRESHOLD = LocalTime.of(9, 0);

    private static final LocalTime LARK_SLEEP_THRESHOLD = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_THRESHOLD = LocalTime.of(7, 0);

    @Override
    public String getName() {
        return "Хронотип пользователя";
    }

    @Override
    public Object analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return Chronotype.PIGEON;
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
            return Chronotype.PIGEON;
        }

        Map<Chronotype, Long> chronotypeCounts = LongStream.rangeClosed(0, daysBetween)
                .mapToObj(firstNight::plusDays)
                .map(night -> getChronotypeForNight(night, sortedSessions))
                .filter(chronotype -> chronotype != null)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return getDominantChronotype(chronotypeCounts);
    }

    private Chronotype getChronotypeForNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime thisNightStart = LocalDateTime.of(night, NIGHT_START);
        LocalDateTime thisNightEnd = LocalDateTime.of(night, NIGHT_END);

        Optional<SleepingSession> session = sessions.stream()
                .filter(s ->
                        (s.getSleepStart().isBefore(thisNightStart) && s.getSleepEnd().isAfter(thisNightStart))
                                ||
                                (s.getSleepStart().isBefore(thisNightEnd) && s.getSleepEnd().isAfter(thisNightEnd)))
                .findFirst();

        if (session.isEmpty()) {
            return null;
        }

        LocalTime sleepTime = session.get().getSleepStart().toLocalTime();
        LocalTime wakeTime = session.get().getSleepEnd().toLocalTime();

        if (sleepTime.isAfter(OWL_SLEEP_THRESHOLD) && wakeTime.isAfter(OWL_WAKE_THRESHOLD)) {
            return Chronotype.OWL;
        }

        if (sleepTime.isBefore(LARK_SLEEP_THRESHOLD) && wakeTime.isBefore(LARK_WAKE_THRESHOLD)) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }

    private Chronotype getDominantChronotype(Map<Chronotype, Long> chronotypeCounts) {
        if (chronotypeCounts.isEmpty()) {
            return Chronotype.PIGEON;
        }

        long maxCount = chronotypeCounts.values().stream().mapToLong(Long::longValue).max().orElse(0);

        long tiedCount = chronotypeCounts.values().stream()
                .filter(c -> c == maxCount)
                .count();

        if (tiedCount > 1) {
            return Chronotype.PIGEON;
        }

        return chronotypeCounts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(Chronotype.PIGEON);
    }
}

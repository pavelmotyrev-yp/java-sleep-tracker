package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.entity.SleepQuality;
import ru.yandex.practicum.sleeptracker.entity.SleepingSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepLogParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private List<SleepingSession> parse(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        }
    }

    private SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        LocalDateTime startTime = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        SleepQuality sleepQuality = SleepQuality.valueOf(parts[2].trim().toUpperCase());
        return new SleepingSession(startTime, endTime, sleepQuality);
    }
}

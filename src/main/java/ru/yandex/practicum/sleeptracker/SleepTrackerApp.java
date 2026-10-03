package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.entity.SleepingSession;
import ru.yandex.practicum.sleeptracker.service.SleepAnalyzer;
import ru.yandex.practicum.sleeptracker.service.SleepAnalyzersCatalog;
import ru.yandex.practicum.sleeptracker.service.SleepLogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {
        try {
            checkArgumentLength(args);

            Path filepath = Path.of(args[0]);
            checkFileExists(filepath);

            List<SleepingSession> sleepingSessions = getSleepingSessionsFromFile(new SleepLogParser(), filepath);

            List<SleepAnalyzer> sleepAnalyzers = new SleepAnalyzersCatalog().getSleepAnalyzers();

            sleepAnalyzers.forEach(sleepAnalyzer -> {
                Object result = sleepAnalyzer.analyze(sleepingSessions);
                System.out.println(sleepAnalyzer.getName() + ": " + result);
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void checkArgumentLength(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Error: sleep tracker file is not specified. " +
                    "Run with an argument: java SleepTrackerApp <file path>");
        }
    }

    private static void checkFileExists(Path filepath) {
        if (Files.notExists(filepath)) {
            throw new RuntimeException("File " + filepath + " does not exist");
        }
    }

    private static List<SleepingSession> getSleepingSessionsFromFile(SleepLogParser sleepLogParser, Path filepath) {
        try {
            return sleepLogParser.parse(filepath);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing sleep tracker file: " + filepath, e);
        }
    }
}
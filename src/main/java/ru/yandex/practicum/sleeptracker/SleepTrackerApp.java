package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.entity.SleepingSession;
import ru.yandex.practicum.sleeptracker.service.SleepLogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: sleep tracker file is not specified. " +
                    "Run with an argument: java SleepTrackerApp <file path>");
            return;
        }

        Path filepath = Path.of(args[0]);
        if (Files.notExists(filepath)) {
            System.err.println("Error: sleep tracker file does not exist: " + filepath);
            return;
        }

        SleepLogParser sleepLogParser = new SleepLogParser();
        List<SleepingSession> sleepingSessions;
        try {
            sleepingSessions = sleepLogParser.parse(filepath);
        } catch (IOException e) {
            System.err.println("Error: reading file: " + filepath);
            return;
        }

    }
}
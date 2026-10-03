package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.entity.SleepQuality;
import ru.yandex.practicum.sleeptracker.entity.SleepingSession;

import java.util.ArrayList;
import java.util.List;

public class SleepAnalyzersCatalog {

    public List<SleepAnalyzer> getSleepAnalyzers() {
        List<SleepAnalyzer> sleepAnalyzers = new ArrayList<>();
        sleepAnalyzers.add(getTotalSessionsAnalyzer());
        sleepAnalyzers.add(getMinDurationAnalyzer());
        sleepAnalyzers.add(getMaxDurationAnalyzer());
        sleepAnalyzers.add(getAverageDurationAnalyzer());
        sleepAnalyzers.add(getBadQualityAnalyzer());
        sleepAnalyzers.add(getSleeplessNightAnalyzer());
        sleepAnalyzers.add(getChronotypeAnalyzer());

        return sleepAnalyzers;
    }

    private SleepAnalyzer getTotalSessionsAnalyzer() {
        return new SleepAnalyzer() {
            @Override
            public String getName() {
                return "Общее колличество сессий сна";
            }

            @Override
            public Object analyze(List<SleepingSession> sessionsList) {
                return sessionsList.size();
            }
        };
    }

    private SleepAnalyzer getMinDurationAnalyzer() {
        return new SleepAnalyzer() {
            @Override
            public String getName() {
                return "Минимальная продолжительность сессии (мин)";
            }

            @Override
            public Object analyze(List<SleepingSession> sessionsList) {
                return sessionsList.stream()
                        .mapToLong(SleepingSession::getDurationSleepInMinutes)
                        .min()
                        .orElse(0);
            }
        };
    }

    private SleepAnalyzer getMaxDurationAnalyzer() {
        return new SleepAnalyzer() {
            @Override
            public String getName() {
                return "Максимальная продолжительность сессии (мин)";
            }

            @Override
            public Object analyze(List<SleepingSession> sessionsList) {
                return sessionsList.stream()
                        .mapToLong(SleepingSession::getDurationSleepInMinutes)
                        .max()
                        .orElse(0);
            }
        };
    }

    private SleepAnalyzer getAverageDurationAnalyzer() {
        return new SleepAnalyzer() {
            @Override
            public String getName() {
                return "Средняя продолжительность сессии (мин)";
            }

            @Override
            public Object analyze(List<SleepingSession> sessionsList) {
                return sessionsList.stream()
                        .mapToLong(SleepingSession::getDurationSleepInMinutes)
                        .average()
                        .orElse(0);
            }
        };
    }

    private SleepAnalyzer getBadQualityAnalyzer() {
        return new SleepAnalyzer() {
            @Override
            public String getName() {
                return "Количество сессий с плохим качеством сна";
            }

            @Override
            public Object analyze(List<SleepingSession> sessionsList) {
                return sessionsList.stream()
                        .filter(session -> session.getSleepQuality().equals(SleepQuality.BAD))
                        .count();
            }
        };
    }

    private SleepAnalyzer getSleeplessNightAnalyzer() {
        return new SleeplessNightAnalyzer();
    }

    private SleepAnalyzer getChronotypeAnalyzer() {
        return new ChronotypeAnalyzer();
    }
}

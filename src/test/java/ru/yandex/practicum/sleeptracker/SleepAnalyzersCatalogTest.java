package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.entity.SleepQuality;
import ru.yandex.practicum.sleeptracker.entity.SleepingSession;
import ru.yandex.practicum.sleeptracker.service.SleepAnalyzersCatalog;
import ru.yandex.practicum.sleeptracker.service.SleepAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SleepAnalyzersCatalogTest {

    private SleepAnalyzersCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new SleepAnalyzersCatalog();
    }

    @Test
    void getTotalSessionsAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getTotalSessionsAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0, result);
    }

    @Test
    void getTotalSessionsAnalyzer_singleSession_returnsOne() {
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepAnalyzer analyzer = catalog.getTotalSessionsAnalyzer();
        Object result = analyzer.analyze(Collections.singletonList(session));
        assertEquals(1, result);
    }

    @Test
    void getTotalSessionsAnalyzer_multipleSessions_returnsCorrectCount() {
        SleepAnalyzer analyzer = catalog.getTotalSessionsAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 15, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 22, 0),
                        LocalDateTime.of(2025, 10, 5, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 5, 23, 0),
                        LocalDateTime.of(2025, 10, 6, 7, 0),
                        SleepQuality.NORMAL
                )
        ));
        assertEquals(5, result);
    }

    @Test
    void getTotalSessionsAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getTotalSessionsAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getMinDurationAnalyzer ====================

    @Test
    void getMinDurationAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getMinDurationAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0L, result);
    }

    @Test
    void getMinDurationAnalyzer_singleSession_returnsItsDuration() {
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepAnalyzer analyzer = catalog.getMinDurationAnalyzer();
        Object result = analyzer.analyze(Collections.singletonList(session));
        assertEquals(480L, result);
    }

    @Test
    void getMinDurationAnalyzer_multipleSessions_returnsMinimum() {
        SleepAnalyzer analyzer = catalog.getMinDurationAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 30),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.BAD
                )
        ));
        assertEquals(90L, result);
    }

    @Test
    void getMinDurationAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getMinDurationAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getMaxDurationAnalyzer ====================

    @Test
    void getMaxDurationAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getMaxDurationAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0L, result);
    }

    @Test
    void getMaxDurationAnalyzer_singleSession_returnsItsDuration() {
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepAnalyzer analyzer = catalog.getMaxDurationAnalyzer();
        Object result = analyzer.analyze(Collections.singletonList(session));
        assertEquals(480L, result);
    }

    @Test
    void getMaxDurationAnalyzer_multipleSessions_returnsMaximum() {
        SleepAnalyzer analyzer = catalog.getMaxDurationAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 30),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.BAD
                )
        ));
        assertEquals(480L, result);
    }

    @Test
    void getMaxDurationAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getMaxDurationAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getAverageDurationAnalyzer ====================

    @Test
    void getAverageDurationAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getAverageDurationAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0.0, result);
    }

    @Test
    void getAverageDurationAnalyzer_singleSession_returnsItsDuration() {
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        );
        SleepAnalyzer analyzer = catalog.getAverageDurationAnalyzer();
        Object result = analyzer.analyze(Collections.singletonList(session));
        assertEquals(480.0, result);
    }

    @Test
    void getAverageDurationAnalyzer_twoSessions_returnsAverage() {
        SleepAnalyzer analyzer = catalog.getAverageDurationAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 30),
                        SleepQuality.NORMAL
                )
        ));
        assertEquals(285.0, result);
    }

    @Test
    void getAverageDurationAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getAverageDurationAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getBadQualityAnalyzer ====================

    @Test
    void getBadQualityAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getBadQualityAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0L, result);
    }

    @Test
    void getBadQualityAnalyzer_noBadSessions_returnsZero() {
        SleepAnalyzer analyzer = catalog.getBadQualityAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.NORMAL
                )
        ));
        assertEquals(0L, result);
    }

    @Test
    void getBadQualityAnalyzer_allBadSessions_returnsAllCount() {
        SleepAnalyzer analyzer = catalog.getBadQualityAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        SleepQuality.BAD
                )
        ));
        assertEquals(3L, result);
    }

    @Test
    void getBadQualityAnalyzer_mixedQuality_returnsBadCount() {
        SleepAnalyzer analyzer = catalog.getBadQualityAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 22, 0),
                        LocalDateTime.of(2025, 10, 5, 6, 0),
                        SleepQuality.BAD
                )
        ));
        assertEquals(2L, result);
    }

    @Test
    void getBadQualityAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getBadQualityAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getSleeplessNightAnalyzer ====================

    @Test
    void getSleeplessNightAnalyzer_emptyList_returnsZero() {
        SleepAnalyzer analyzer = catalog.getSleeplessNightAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals(0L, result);
    }

    @Test
    void getSleeplessNightAnalyzer_allNightsCovered_returnsZero() {
        SleepAnalyzer analyzer = catalog.getSleeplessNightAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        SleepQuality.GOOD
                )
        ));
        assertEquals(0L, result);
    }

    @Test
    void getSleeplessNightAnalyzer_withGaps_returnsSleeplessCount() {
        SleepAnalyzer analyzer = catalog.getSleeplessNightAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 5, 23, 0),
                        LocalDateTime.of(2025, 10, 6, 7, 0),
                        SleepQuality.GOOD
                )
        ));
        assertEquals(3L, result);
    }

    @Test
    void getSleeplessNightAnalyzer_firstSessionAfterNoon_adjustsFirstNight() {
        SleepAnalyzer analyzer = catalog.getSleeplessNightAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                )
        ));
        assertEquals(1L, result);
    }

    @Test
    void getSleeplessNightAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getSleeplessNightAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }

    // ==================== getChronotypeAnalyzer ====================

    @Test
    void getChronotypeAnalyzer_emptyList_returnsPigeon() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        Object result = analyzer.analyze(Collections.emptyList());
        assertEquals("PIGEON", result.toString());
    }

    @Test
    void getChronotypeAnalyzer_allOwlNights_returnsOwl() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 15),
                        LocalDateTime.of(2025, 10, 4, 10, 0),
                        SleepQuality.GOOD
                )
        ));
        assertEquals("OWL", result.toString());
    }

    @Test
    void getChronotypeAnalyzer_allLarkNights_returnsLark() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 5, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 20, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 15),
                        LocalDateTime.of(2025, 10, 4, 5, 45),
                        SleepQuality.GOOD
                )
        ));
        assertEquals("LARK", result.toString());
    }

    @Test
    void getChronotypeAnalyzer_equalOwlAndLark_returnsPigeon() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        SleepQuality.NORMAL
                )
        ));
        assertEquals("PIGEON", result.toString());
    }

    @Test
    void getChronotypeAnalyzer_withSleeplessNights_ignoresThem() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        Object result = analyzer.analyze(Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 5, 23, 30),
                        LocalDateTime.of(2025, 10, 6, 10, 0),
                        SleepQuality.GOOD
                )
        ));
        assertEquals("OWL", result.toString());
    }

    @Test
    void getChronotypeAnalyzer_nullList_throwsNullPointerException() {
        SleepAnalyzer analyzer = catalog.getChronotypeAnalyzer();
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }
}

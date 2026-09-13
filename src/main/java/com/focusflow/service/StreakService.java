package com.focusflow.service;

import com.focusflow.domain.DailyStat;
import com.focusflow.domain.FocusSession;
import com.focusflow.repo.DailyStatRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class StreakService {

    private final DailyStatRepository dailyStatRepository;
    private final Clock clock;

    public StreakService(DailyStatRepository dailyStatRepository, Clock clock) {
        this.dailyStatRepository = dailyStatRepository;
        this.clock = clock;
    }

    public void recordCompletedSession(FocusSession session) {
        LocalDate date = LocalDate.now(clock);
        DailyStat stat = dailyStatRepository.findByStatDate(date)
                .orElseGet(() -> new DailyStat(date));
        stat.recordCompletedSession((int) session.actualMinutes());
        dailyStatRepository.save(stat);
    }

    public int currentStreak() {
        List<DailyStat> stats = dailyStatRepository.findAllByOrderByStatDateDesc();
        int streak = 0;
        LocalDate expected = LocalDate.now(clock);

        for (DailyStat stat : stats) {
            if (stat.getCompletedSessions() == 0 || !stat.getStatDate().equals(expected)) {
                break;
            }
            streak++;
            expected = expected.minusDays(1);
        }
        return streak;
    }

    public int longestStreak() {
        List<DailyStat> stats = dailyStatRepository.findAllByOrderByStatDateDesc();
        int longest = 0;
        int current = 0;
        LocalDate previous = null;

        for (DailyStat stat : stats) {
            if (stat.getCompletedSessions() == 0) {
                current = 0;
                previous = null;
                continue;
            }
            current = (previous == null || previous.minusDays(1).equals(stat.getStatDate())) ? current + 1 : 1;
            longest = Math.max(longest, current);
            previous = stat.getStatDate();
        }
        return longest;
    }
}

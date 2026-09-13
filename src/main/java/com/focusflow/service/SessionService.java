package com.focusflow.service;

import com.focusflow.config.FocusRules;
import com.focusflow.domain.FocusSession;
import com.focusflow.domain.SessionStatus;
import com.focusflow.repo.FocusSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class SessionService {

    private final FocusSessionRepository sessionRepository;
    private final GardenService gardenService;
    private final StreakService streakService;
    private final FocusRules focusRules;
    private final Clock clock;

    public SessionService(FocusSessionRepository sessionRepository,
                           GardenService gardenService,
                           StreakService streakService,
                           FocusRules focusRules,
                           Clock clock) {
        this.sessionRepository = sessionRepository;
        this.gardenService = gardenService;
        this.streakService = streakService;
        this.focusRules = focusRules;
        this.clock = clock;
    }

    public FocusSession startSession(int plannedMinutes) {
        sessionRepository.findFirstByStatus(SessionStatus.RUNNING).ifPresent(existing -> {
            throw new SessionAlreadyRunningException("Session " + existing.getId() + " is already running");
        });

        int minutes = clampToRules(plannedMinutes);
        Instant now = clock.instant();
        FocusSession session = new FocusSession(now, minutes, LocalDate.now(clock));
        return sessionRepository.save(session);
    }

    public FocusSession completeSession(Long sessionId) {
        FocusSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("No session with id " + sessionId));

        session.complete(clock.instant());
        FocusSession saved = sessionRepository.save(session);

        streakService.recordCompletedSession(saved);
        gardenService.applyGrowth(saved);

        return saved;
    }

    public FocusSession abandonSession(Long sessionId) {
        FocusSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("No session with id " + sessionId));

        session.abandon(clock.instant());
        return sessionRepository.save(session);
    }

    public FocusSession currentSession() {
        return sessionRepository.findFirstByStatus(SessionStatus.RUNNING).orElse(null);
    }

    private int clampToRules(int requestedMinutes) {
        return Math.max(focusRules.getMinMinutes(), Math.min(focusRules.getMaxMinutes(), requestedMinutes));
    }
}

package com.focusflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "focus_sessions")
public class FocusSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant endedAt;

    @Column(nullable = false)
    private int plannedMinutes;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    protected FocusSession() {
    }

    public FocusSession(Instant startedAt, int plannedMinutes, LocalDate sessionDate) {
        this.startedAt = startedAt;
        this.plannedMinutes = plannedMinutes;
        this.sessionDate = sessionDate;
        this.status = SessionStatus.RUNNING;
    }

    public void complete(Instant endedAt) {
        this.endedAt = endedAt;
        this.status = SessionStatus.COMPLETED;
    }

    public void abandon(Instant endedAt) {
        this.endedAt = endedAt;
        this.status = SessionStatus.ABANDONED;
    }

    public long actualMinutes() {
        if (endedAt == null) {
            return 0;
        }
        return Duration.between(startedAt, endedAt).toMinutes();
    }

    public Long getId() {
        return id;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public SessionStatus getStatus() {
        return status;
    }
}

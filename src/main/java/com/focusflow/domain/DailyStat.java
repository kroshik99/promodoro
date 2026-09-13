package com.focusflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;

@Entity
@Table(name = "daily_stats", uniqueConstraints = @UniqueConstraint(columnNames = "statDate"))
public class DailyStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate statDate;

    @Column(nullable = false)
    private int completedSessions;

    @Column(nullable = false)
    private int focusMinutes;

    protected DailyStat() {
    }

    public DailyStat(LocalDate statDate) {
        this.statDate = statDate;
    }

    public void recordCompletedSession(int minutes) {
        this.completedSessions++;
        this.focusMinutes += minutes;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStatDate() {
        return statDate;
    }

    public int getCompletedSessions() {
        return completedSessions;
    }

    public int getFocusMinutes() {
        return focusMinutes;
    }
}

package com.focusflow.web;

import com.focusflow.domain.SessionStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public final class Dtos {

    private Dtos() {
    }

    public record CreateSessionRequest(
            @NotNull(message = "minutes is required")
            @Min(value = 1, message = "minutes must be at least 1")
            @Max(value = 180, message = "minutes must be at most 180")
            Integer minutes
    ) {
    }

    public record SessionResponse(
            Long id,
            Instant startedAt,
            Instant endedAt,
            int plannedMinutes,
            SessionStatus status,
            String nudge
    ) {
    }

    public record PlantResponse(
            Long id,
            String name,
            int growthPoints,
            String stage
    ) {
    }

    public record GardenResponse(
            List<PlantResponse> plants,
            int totalGrowthPoints,
            int currentStreak,
            int longestStreak
    ) {
    }

    public record ErrorResponse(
            String error,
            String message
    ) {
    }
}

package com.focusflow.web;

import com.focusflow.domain.FocusSession;
import com.focusflow.domain.Plant;
import com.focusflow.service.GardenService;
import com.focusflow.service.SessionService;
import com.focusflow.service.StreakService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FocusController {

    private final SessionService sessionService;
    private final GardenService gardenService;
    private final StreakService streakService;
    private final NudgeCopy nudgeCopy;

    public FocusController(SessionService sessionService,
                            GardenService gardenService,
                            StreakService streakService,
                            NudgeCopy nudgeCopy) {
        this.sessionService = sessionService;
        this.gardenService = gardenService;
        this.streakService = streakService;
        this.nudgeCopy = nudgeCopy;
    }

    @PostMapping("/sessions")
    public ResponseEntity<Dtos.SessionResponse> startSession(@Valid @RequestBody Dtos.CreateSessionRequest request) {
        FocusSession session = sessionService.startSession(request.minutes());
        return ResponseEntity.ok(toResponse(session));
    }

    @PostMapping("/sessions/{id}/complete")
    public ResponseEntity<Dtos.SessionResponse> completeSession(@PathVariable Long id) {
        FocusSession session = sessionService.completeSession(id);
        return ResponseEntity.ok(toResponse(session));
    }

    @PostMapping("/sessions/{id}/abandon")
    public ResponseEntity<Dtos.SessionResponse> abandonSession(@PathVariable Long id) {
        FocusSession session = sessionService.abandonSession(id);
        return ResponseEntity.ok(toResponse(session));
    }

    @GetMapping("/sessions/current")
    public ResponseEntity<Dtos.SessionResponse> currentSession() {
        FocusSession session = sessionService.currentSession();
        if (session == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(toResponse(session));
    }

    @GetMapping("/garden")
    public ResponseEntity<Dtos.GardenResponse> garden() {
        List<Dtos.PlantResponse> plants = gardenService.plants().stream()
                .map(this::toResponse)
                .toList();

        Dtos.GardenResponse response = new Dtos.GardenResponse(
                plants,
                gardenService.totalGrowthPoints(),
                streakService.currentStreak(),
                streakService.longestStreak()
        );
        return ResponseEntity.ok(response);
    }

    private Dtos.SessionResponse toResponse(FocusSession session) {
        return new Dtos.SessionResponse(
                session.getId(),
                session.getStartedAt(),
                session.getEndedAt(),
                session.getPlannedMinutes(),
                session.getStatus(),
                nudgeCopy.forStatus(session.getStatus())
        );
    }

    private Dtos.PlantResponse toResponse(Plant plant) {
        return new Dtos.PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getGrowthPoints(),
                plant.stageName()
        );
    }
}

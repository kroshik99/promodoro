package com.focusflow.service;

import com.focusflow.domain.FocusSession;
import com.focusflow.domain.Plant;
import com.focusflow.repo.PlantRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

@Service
public class GardenService {

    private static final int POINTS_PER_MINUTE = 1;
    private static final int POINTS_TO_NEW_PLANT = 100;

    private final PlantRepository plantRepository;
    private final Clock clock;

    public GardenService(PlantRepository plantRepository, Clock clock) {
        this.plantRepository = plantRepository;
        this.clock = clock;
    }

    public List<Plant> plants() {
        return plantRepository.findAllByOrderByPlantedAtAsc();
    }

    public void applyGrowth(FocusSession completedSession) {
        int points = (int) completedSession.actualMinutes() * POINTS_PER_MINUTE;

        Plant plant = plantRepository.findAllByOrderByPlantedAtAsc().stream()
                .filter(p -> p.getGrowthPoints() < POINTS_TO_NEW_PLANT)
                .findFirst()
                .orElseGet(() -> plantRepository.save(new Plant(nextPlantName(), clock.instant())));

        plant.grow(points);
        plantRepository.save(plant);
    }

    public int totalGrowthPoints() {
        return plantRepository.findAll().stream()
                .mapToInt(Plant::getGrowthPoints)
                .sum();
    }

    private String nextPlantName() {
        long count = plantRepository.count();
        return "Plant #" + (count + 1);
    }
}

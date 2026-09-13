package com.focusflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "plants")
public class Plant {

    private static final int SPROUT_THRESHOLD = 20;
    private static final int BUDDING_THRESHOLD = 60;
    private static final int FULLY_GROWN_THRESHOLD = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int growthPoints;

    @Column(nullable = false)
    private Instant plantedAt;

    protected Plant() {
    }

    public Plant(String name, Instant plantedAt) {
        this.name = name;
        this.plantedAt = plantedAt;
        this.growthPoints = 0;
    }

    public void grow(int points) {
        this.growthPoints = Math.min(FULLY_GROWN_THRESHOLD, this.growthPoints + points);
    }

    public boolean isFullyGrown() {
        return growthPoints >= FULLY_GROWN_THRESHOLD;
    }

    public String stageName() {
        if (growthPoints >= FULLY_GROWN_THRESHOLD) {
            return "bloomed";
        }
        if (growthPoints >= BUDDING_THRESHOLD) {
            return "budding";
        }
        if (growthPoints >= SPROUT_THRESHOLD) {
            return "sprout";
        }
        return "seed";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGrowthPoints() {
        return growthPoints;
    }

    public Instant getPlantedAt() {
        return plantedAt;
    }
}

package com.focusflow.repo;

import com.focusflow.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findAllByOrderByPlantedAtAsc();
}

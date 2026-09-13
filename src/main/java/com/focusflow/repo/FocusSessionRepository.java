package com.focusflow.repo;

import com.focusflow.domain.FocusSession;
import com.focusflow.domain.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {

    Optional<FocusSession> findFirstByStatus(SessionStatus status);

    List<FocusSession> findBySessionDateOrderByStartedAtDesc(LocalDate sessionDate);

    long countByStatus(SessionStatus status);
}

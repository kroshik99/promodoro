package com.focusflow.web;

import com.focusflow.domain.SessionStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class NudgeCopy {

    private static final List<String> STARTED = List.of(
            "Focus time started. You've got this.",
            "Heads down. One session at a time.",
            "Timer's running. Let's grow something."
    );

    private static final List<String> COMPLETED = List.of(
            "Session complete. Your garden just grew a little.",
            "Nice work. Go water that streak.",
            "Done. That's another seed planted."
    );

    private static final List<String> ABANDONED = List.of(
            "Session stopped early. There's always the next one.",
            "No streak lost over one skipped session. Try again soon."
    );

    public String forStatus(SessionStatus status) {
        List<String> pool = switch (status) {
            case RUNNING -> STARTED;
            case COMPLETED -> COMPLETED;
            case ABANDONED -> ABANDONED;
        };
        return pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
    }
}

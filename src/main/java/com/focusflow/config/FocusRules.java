package com.focusflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "focus.rules")
public class FocusRules {

    private int minMinutes = 5;
    private int maxMinutes = 120;
    private int defaultMinutes = 25;
    private int dailyGoalSessions = 4;

    public int getMinMinutes() {
        return minMinutes;
    }

    public void setMinMinutes(int minMinutes) {
        this.minMinutes = minMinutes;
    }

    public int getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(int maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public int getDefaultMinutes() {
        return defaultMinutes;
    }

    public void setDefaultMinutes(int defaultMinutes) {
        this.defaultMinutes = defaultMinutes;
    }

    public int getDailyGoalSessions() {
        return dailyGoalSessions;
    }

    public void setDailyGoalSessions(int dailyGoalSessions) {
        this.dailyGoalSessions = dailyGoalSessions;
    }
}

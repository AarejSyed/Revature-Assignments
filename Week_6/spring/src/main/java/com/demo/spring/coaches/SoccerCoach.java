package com.demo.spring.coaches;

import com.demo.spring.Coach;
import org.springframework.stereotype.Component;

@Component
public class SoccerCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Practice your penalty kicks for 30 minutes";
    }
}

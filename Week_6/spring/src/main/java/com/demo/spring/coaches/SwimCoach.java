package com.demo.spring.coaches;

import com.demo.spring.Coach;

public class SwimCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Swim for 30 minutes";
    }
}

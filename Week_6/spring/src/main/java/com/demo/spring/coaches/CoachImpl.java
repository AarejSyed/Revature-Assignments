package com.demo.spring.coaches;

import org.springframework.stereotype.Component;

import com.demo.spring.Coach;

@Component
public class CoachImpl implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Run for 15 minutes";
    }
}

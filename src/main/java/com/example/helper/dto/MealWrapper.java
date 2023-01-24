package com.example.helper.dto;

import com.example.helper.entity.Meal;
import lombok.Getter;

@Getter
public class MealWrapper {
    public MealWrapper(Meal meal) {
        this.meal = meal;
    }
    private Meal meal;
}

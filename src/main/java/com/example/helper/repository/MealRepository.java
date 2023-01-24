package com.example.helper.repository;

import com.example.helper.entity.Meal;

import java.util.Optional;

public interface MealRepository {
    Meal save(Meal meal);

    Optional<Meal> findIdByDateTitleKind(String title, String meal_date, String kind_of_meal);

    Optional<Meal> findById(Long mealId);

    Long delete(Long mealId);
}
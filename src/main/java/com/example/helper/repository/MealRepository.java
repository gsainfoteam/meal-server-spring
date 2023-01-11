package com.example.helper.repository;

import com.example.helper.entity.Meal;

public interface MealRepository {
    Meal save(Meal meal);

    Long findIdByDateTitleKind(String title, String meal_date, String kind_of_meal);

    Meal findById(Long mealId);

    Long delete(Long mealId);
}
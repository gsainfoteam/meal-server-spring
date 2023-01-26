package com.example.helper.repository;

import com.example.helper.entity.Meal;

import java.util.Optional;

public interface MealRepository {
    Meal save(Meal meal);

    Optional<Meal> findByPk(Integer bldgType, Integer langType, Integer dateType, Integer kindType, String date);

    Optional<Meal> findById(Long mealId);

    Long delete(Long mealId);
}
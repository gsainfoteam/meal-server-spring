package com.example.helper.service;

import com.example.helper.entity.Meal;
import com.example.helper.repository.MealRepository;
import com.example.helper.repository.SqlMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    @Autowired(required = true)
    private MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Long mealCreate(Meal meal) {
        // 중복 체크는 없음
        validateDuplicateMeal(meal);
        mealRepository.save(meal);
        return meal.getId();
    }

    private void validateDuplicateMeal(Meal meal) {
        mealRepository.findIdByDateTitleKind(meal.getTitle(), meal.getMeal_date(), meal.getKind_of_meal())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}

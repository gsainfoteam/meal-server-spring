package com.example.helper.controller;
import com.example.helper.dto.MealWrapper;
import com.example.helper.dto.Mealdto;
import com.example.helper.entity.Meal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MeealController {

    @Autowired
    private MealRepository mealRepository;

    @PostMapping("/meals/create")
    public @ResponseBody createMeal(MealWrapper mealWrapper) {

        Meal meal = mealWrapper.getMeal();

        Meal saved = mealRepository.save(meal);

        return saved; // Responsebody 넣어서 보내는거 해보세요
    }

}

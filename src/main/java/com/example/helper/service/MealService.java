package com.example.helper.service;

import com.example.helper.entity.Meal;
import com.example.helper.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;
//
//    public Meal save(Meal mael) {
//        Meal n = new Meal();
//
//        n.setTitle(meal.getTitle());
//        n.setStart(reqMeal.getStart());
//        n.setEnd(reqMeal.getEnd());
//        n.setUser(reqMeal.getUser());
//
//        // 중복 모르겠고 일단 다 넣자.
////        if (
////                !timetableService.validateDay(reqTime.getDay())
////                        || !timetableService.validateTime(reqTime.getStart(), reqTime.getEnd())
////                        || timetableService.isDuplicateNew(n, newBuildingTimetableRepository.findByDay(reqTime.getDay()))
////        ) {
////            Throwable ex = new Throwable();
////            throw new ResponseStatusException(
////                    HttpStatus.NOT_ACCEPTABLE, "Invalid day, time", ex);
////        }
////        mealRepository.save(n);
//    }
//
//    public Meal findByMeal(Meal meal) {
//        Meal result = new Meal();
//        Integer target = mealRepository.findByDateTitlealfajflkfdj()
//
//    }
}
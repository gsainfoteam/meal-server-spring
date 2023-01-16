package com.example.helper.repository;

import com.example.helper.entity.Meal;
import org.springframework.stereotype.Repository;

@Repository
public class SqliteMealRepository implements MealRepository {
    @Override
    public Meal save(Meal meal) {
        return null;
    }

    @Override
    public Long findIdByDateTitleKind(String title, String meal_date, String kind_of_meal) {
        return null;
    }

    @Override
    public Meal findById(Long mealId) {
        return null;
    }

    @Override
    public Long delete(Long mealId) {
        return null;
    }

//    private final SqliteConnection sqliteConnection;
//
//    public SqliteMealRepository(SqliteConnection sqliteConnection) {
//        this.sqliteConnection = sqliteConnection;
//    }
//
//    @Override
//    public Meal save(Meal meal) {
//        String sql = "INSERT INTO meal (title, meal_date, kind_of_meal, menu) VALUES (?, ?, ?, ?)";
//        Long mealId = sqliteConnection.insert(sql, meal.getTitle(), meal.getMeal_date(), meal.getKind_of_meal(),
//                meal.getMenu());
//        return findById(meal.getId());
//    }


}

package com.example.helper.repository;

import com.example.helper.entity.Meal;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SqlMealRepository implements MealRepository {
    private final EntityManager em;
    public SqlMealRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Meal save(Meal meal) {
        em.persist(meal);
        return meal;
    }

    @Override
    public Optional<Meal> findIdByDateTitleKind(String title, String meal_date, String kind_of_meal) {
        List<Meal> result = em.createQuery("select m from Member m where m.title = :title and m.meal_date = :meal_date and m.kind_of_meal = :kind_of_meal", Meal.class)
                .setParameter("title", title)
                .setParameter("meal_date", meal_date)
                .setParameter("kind_of_meal", kind_of_meal)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Meal> findById(Long mealId) {
        Meal meal = em.find(Meal.class, mealId);
        return Optional.ofNullable(meal);
    }

    @Override
    public Long delete(Long mealId) {
        return null;
    }
}

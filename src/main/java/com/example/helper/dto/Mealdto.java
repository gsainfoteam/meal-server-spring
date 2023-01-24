package com.example.helper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Mealdto {
    public Mealdto(String title, String meal_date, String kind_of_meal, String menu) {
        this.title = title;
        this.meal_date = meal_date;
        this.kind_of_meal = kind_of_meal;
        this.menu = menu;
    }
    private String title;
    private String meal_date;
    private String kind_of_meal;
    private String menu;
}

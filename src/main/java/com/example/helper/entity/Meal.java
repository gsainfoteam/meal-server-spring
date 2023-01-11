package com.example.helper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Meal {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String meal_date;

    @Column
    private String kind_of_meal;

    @Column
    private String menu;
}

package com.example.helper.dto;

import jakarta.persistence.Column;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mealdto {
    private Integer bldgType;
    private Integer langType;
    private Integer dateType;
    private Integer kindType;
    private String bldg;
    private String date;
    private String kind;
    private String menu;
    private String special;
}

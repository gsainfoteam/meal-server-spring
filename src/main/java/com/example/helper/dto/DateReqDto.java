package com.example.helper.dto;

import lombok.Data;

@Data
public class DateReqDto {
    private String year;
    private String month;
    private String date;
    private Integer langType;
    private Integer bldgType;
}

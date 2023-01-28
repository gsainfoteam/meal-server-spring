package com.example.helper.service;

import com.example.helper.dto.DateMealDto;
import com.example.helper.dto.DateReqDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DateMealService {
    // TODO: exception handling(target data is not in DB)

    public List<DateMealDto> getDateMeal(DateReqDto dateReqDto) {
        return null;
    }
}

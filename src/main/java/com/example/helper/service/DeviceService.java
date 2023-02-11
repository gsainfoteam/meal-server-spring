package com.example.helper.service;

import com.example.helper.constant.Types;
import com.example.helper.dto.DateMealDto;
import com.example.helper.dto.DateReqDto;
import com.example.helper.entity.Meal;
import com.example.helper.repository.SqlMealRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class DeviceService {

    @Autowired
    private SqlMealRepository sqlMealRepository;

    public DateMealDto getDateMenus(DateReqDto dateReqDto) {
        DateMealDto result = DateMealDto.builder()
                .breakfast(getMenuFromMeal(dateReqDto, Types.KIND_BREAKFAST.getType()))
                .lunch(getMenuFromMeal(dateReqDto, Types.KIND_LUNCH.getType()))
                .dinner(getMenuFromMeal(dateReqDto, Types.KIND_DINNER.getType()))
                .lunch_corner((getMenuFromMeal(dateReqDto, Types.KIND_LUNCH_CORNER.getType())))
                .build();

        // TODO: exception handling(target data is not in DB)
        if (checkMenusEmpty(result)) {
            throw new IllegalStateException("해당 날짜의 식단이 존재하지 않습니다.");
        }
        return result;
    }

    public String getMenuFromMeal(DateReqDto dateReqDto, Integer kindType) {
        String result = "";

        // DB에는 KIND_TYPE이 0,1,2만 존재. 따라서 KIND_LUNCH_CORNER는 KIND_LUNCH로 찾아서 getSpecial해야함.
        Integer temp = kindType; // Integer는 immutable이므로 '='로 복사가능
        if (kindType.equals(Types.KIND_LUNCH_CORNER.getType())) {
            temp  = Types.KIND_LUNCH.getType();
        }

        Optional<Meal> meal = sqlMealRepository.findByDate(
                dateReqDto.getBldgType(),
                dateReqDto.getLangType(),
                temp,
                conv2DateStr(dateReqDto.getYear(), dateReqDto.getMonth(), dateReqDto.getDate()));

        // TODO: 함수 분리, TEST CODE 작성
        if (meal.isPresent()) {
            result = meal.get().getMenu();

            if (kindType.equals(Types.KIND_LUNCH_CORNER.getType())) {
                result = meal.get().getSpecial();
                result = (result.equals("")) ? "\n" : result;
            }
            else if (kindType.equals(Types.KIND_LUNCH.getType())) {
                // 이제 parsing 단계에서 이 예외는 발생안하게 고쳤으나, 혹시 모르니 남겨둠
                result = (result.substring(result.length() - 2).equals("\n\n")) ?
                        result.substring(0, result.length() - 1) :
                        result;
            }
        }
        return result;
    }

    public String conv2DateStr(String year, String month, String date) {
        String result = "";
        result = year + "-" + padZero(month) + "-" + padZero(date);
        return result;
    }

    public String padZero(String str) {
        String result = "";
        if (str.length() == 1) {
            result = "0" + str;
        } else {
            result = str;
        }
        return result;
    }

    public boolean checkMenusEmpty(DateMealDto dateMealDto) {
        return dateMealDto.getBreakfast().isEmpty() &&
                dateMealDto.getLunch().isEmpty() &&
                dateMealDto.getDinner().isEmpty() &&
                dateMealDto.getLunch_corner().isEmpty();
    }
}

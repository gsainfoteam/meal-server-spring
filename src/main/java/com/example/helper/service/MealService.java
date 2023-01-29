package com.example.helper.service;

import com.example.helper.constant.Messages;
import com.example.helper.constant.SpecMealInputsKor;
import com.example.helper.constant.Types;
import com.example.helper.dto.DateMealDto;
import com.example.helper.dto.DateReqDto;
import com.example.helper.entity.Meal;
import com.example.helper.repository.MealRepository;
import com.example.helper.repository.SqlMealRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
@Slf4j
public class MealService {

    @Autowired(required = true)
    private SqlMealRepository sqlMealRepository;

    public MealService(SqlMealRepository sqlMealRepository) {
        this.sqlMealRepository = sqlMealRepository;
    }

    public Long mealCreate(Meal meal) {
        validateDuplicateMeal(meal);
        sqlMealRepository.save(meal);
        return meal.getId();
    }

    private void validateDuplicateMeal(Meal meal) {
        // DB 중복 체크
        sqlMealRepository.findByPk(meal.getBldgType(), meal.getLangType(), meal.getDateType(), meal.getKindType(), meal.getDate())
                .ifPresent(m -> {
                    throw new IllegalStateException(Messages.EXIST_MEAL_ERROR.getMessages());
                });
    }

    private String getNowMeal(Integer langType) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Integer hour = currentDateTime.getHour();
        Integer kindType = 0;
        if(9 <= hour && hour < 13) {
            kindType = 1;
        }
        else if(13 <= hour && hour < 19) {
            kindType = 2;
        }
        else if(19 <= hour && hour < 24) {
            kindType = 0;
            currentDateTime = currentDateTime.plusDays(1);
        }
        else {
            kindType = 0;
            currentDateTime = currentDateTime.plusDays(1);
        }

        String date = currentDateTime.getYear() + "-";
        date += String.format("%02d", currentDateTime.getMonth().getValue()) + "-";
        date += String.format("%02d", currentDateTime.getDayOfMonth()) + "";

        Optional<Meal> result = sqlMealRepository.findByDate(Types.BLDG2_1ST.getType(), langType, kindType, date);

        // TODO: 함수로 분리
        if(result.isEmpty()) {
            //throw new IllegalStateException(Messages.EXIST_MEAL_ERROR.getMessages());
            if(langType == 0) {
                return Messages.NO_MEAL_KOR.getMessages();
            }
            else {
                return Messages.NO_MEAL_ENG.getMessages();
            }
        }
        Meal meal = result.get();
        return meal.generateMenu();
    }

    private Boolean specInputValidation(String dateCustom, String bld) {
        // input arguments : null, empty, " "
        if (dateCustom == null || bld == null || dateCustom.isBlank() || bld.isBlank()) {
            return false;
        }
        return true;
    }

    public String getNowKorMeal() {
        return getNowMeal(Types.LANG_KOR.getType());
    }

    public String getNowEngMeal() {
        return getNowMeal(Types.LANG_ENG.getType());
    }

    public String getSpecKorMeal(String dateCustom, String bld, LocalDateTime currentDateTime) {
        // test를 위해 LocalDateTime 객체를 argument로 받도록 변경

        if (!specInputValidation(dateCustom, bld)) {
            return Messages.NO_MEAL_KOR.getMessages();
        }

        try {
            if (dateCustom.equals(SpecMealInputsKor.TOMORROW.getInputs())) {
                // 내일
                currentDateTime = currentDateTime.plusDays(1);
            } else if (dateCustom.length() == 1) {
                // 요일
                Integer dateDiff = getDateDifference(dateCustom, currentDateTime);
                currentDateTime = currentDateTime.plusDays(dateDiff);
            } else if ((dateCustom.charAt(dateCustom.length() - 1) + "").equals(SpecMealInputsKor.DAY.getInputs())) {
                // 특정날짜
                currentDateTime = currentDateTime.withDayOfMonth(
                        Integer.parseInt(dateCustom.substring(0, dateCustom.length() - 1)));
            }
        } catch (Exception e) {
            return Messages.INVALID_DATE.getMessages();
        }

        String date = currentDateTime.getYear() + "-";
        date += String.format("%02d", currentDateTime.getMonth().getValue()) + "-";
        date += String.format("%02d", currentDateTime.getDayOfMonth()) + "";

        Optional<Meal> result = sqlMealRepository.findByDate(
                Types.BLDG2_1ST.getType(),
                Types.LANG_KOR.getType(),
                SpecMealInputsKor.getTypeByString(bld),
                date);

        // TODO : 존재하지 않는 식단이면, error message 반환.
        if(result.isEmpty()) {
            return Messages.NO_MEAL_KOR.getMessages();
        }

        return result.get().generateMenu();
    }

    public Integer getDateDifference(String day, LocalDateTime currentDateTime) {
        return (Integer) currentDateTime.getDayOfWeek().getValue() - SpecMealInputsKor.getTypeByString(day);
    }

    public String getSpecEngMeal(String dateCustom, String bld) {
        if (!specInputValidation(dateCustom, bld)) {
            return Messages.NO_MEAL_ENG.getMessages();
        }
        return "2023-01-27 Breakfast\n\nStudent Union Bldg.2 1st floor\n\nWhite rice*Seasoned rice with seaweed\n";
    }

    public Map<String, Object> responseMeal(String menu) {
        // Response Body Construct
        // responseBody: {
        //      version: "2.0",
        //          template: {
        //              outputs: [
        //                  {
        //                      simpleText:
        //                          {
        //                              text: meal
        //                           }
        //                   }
        //              ]
        //       }
        // };

        Map<String, Object> simpleText =  new HashMap<>();
        simpleText.put("text", menu);

        Map<String, Object> simpleTextWrapper = new HashMap<>();
        simpleTextWrapper.put("simpleText", simpleText);

        List<Object> outputs = new ArrayList(1);
        outputs.add(simpleTextWrapper);

        Map<String, Object> template = new HashMap<>();
        template.put("outputs", outputs);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("version", "2.0");
        responseBody.put("template", template);

        // stratify json to string
        // ObjectMapper objectMapper = new ObjectMapper();
        // String result = objectMapper.writeValueAsString(responseBody);

        return responseBody;
    }
}

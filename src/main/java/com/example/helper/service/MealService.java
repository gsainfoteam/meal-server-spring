package com.example.helper.service;

import com.example.helper.constant.Messages;
import com.example.helper.constant.SpecMealInputsEng;
import com.example.helper.constant.SpecMealInputsKor;
import com.example.helper.constant.Types;
import com.example.helper.dto.DateMealDto;
import com.example.helper.dto.DateReqDto;
import com.example.helper.entity.Meal;
import com.example.helper.repository.MealRepository;
import com.example.helper.repository.SqlMealRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        // len = 0 or null or ...
        Boolean ret = true;

        return ret;
    }

    public String getNowKorMeal() {
        return getNowMeal(Types.LANG_KOR.getType());
    }

    public String getNowEngMeal() {
        return getNowMeal(Types.LANG_ENG.getType());
    }

    public String getSpecKorMeal(String dateCustom, String bld) {
        if (!specInputValidation(dateCustom, bld)) {
            return Messages.NO_MEAL_KOR.getMessages();
        }

        if(dateCustom.equals(SpecMealInputsKor.TODAY.getInputs())) {
            // 오늘
        }
        else if(dateCustom.equals(SpecMealInputsKor.TOMORROW.getInputs())) {
            // 내일
        }
        else if(dateCustom.length() == 1) {
            // 요일
        }
        else if((dateCustom.charAt(dateCustom.length() - 1) + "").equals(SpecMealInputsKor.DAY.getInputs())) {
            // 특정날짜
        }

        return "2023-01-27 조식\n\n제2학생회관1층\n\n흰밥*김가루양념밥\n";
    }
    public Boolean dateFormatValidation(String date) {
        Boolean ret = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);

        } catch(ParseException e) {
            ret = false;
        }
        return  ret;
    }
    public String getSpecEngMeal(String dateCustom, String bld) {
        if (!specInputValidation(dateCustom, bld)) {
            return Messages.NO_MEAL_ENG.getMessages();
        }
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Integer year = currentDateTime.getYear();
        Integer month = currentDateTime.getMonth().getValue();
        Integer day = currentDateTime.getDayOfMonth();
        Integer currentDateNumber = currentDateTime.getDayOfWeek().getValue();

        Integer dateCustomLen = dateCustom.length();
        if(dateCustom.equals(SpecMealInputsEng.TODAY.getInputs())) {
            // 오늘
        }
        else if(dateCustom.equals(SpecMealInputsEng.TOMORROW.getInputs())) {
            // 내일
            currentDateTime = currentDateTime.plusDays(1);
            year = currentDateTime.getYear();
            month = currentDateTime.getMonth().getValue();
            day = currentDateTime.getDayOfMonth();
        }
        else if(dateCustomLen > 2) {
            String dateCustomPostfix = dateCustom.substring(dateCustomLen - 2, dateCustomLen);
            if( dateCustomPostfix.equals(SpecMealInputsEng.DAY_1.getInputs()) ||
                    dateCustomPostfix.equals(SpecMealInputsEng.DAY_2.getInputs()) ||
                    dateCustomPostfix.equals(SpecMealInputsEng.DAY_3.getInputs()) ||
                    dateCustomPostfix.equals(SpecMealInputsEng.DAY_OTHER.getInputs())) {
                // 특정날짜
                String dateCustomPrefix = (dateCustomLen == 3) ? dateCustom.substring(0, 1) : dateCustom.substring(0, 2);

                try{
                    day = Integer.valueOf(dateCustomPrefix);
                }
                catch (NumberFormatException ex){
                    day = 0;
                }
            }
            else {
                //요일
                Integer dateNumber = 0;
                if(dateCustom.equals(SpecMealInputsEng.MON.getInputs())) {
                    dateNumber = SpecMealInputsEng.MON.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.TUE.getInputs())) {
                    dateNumber = SpecMealInputsEng.TUE.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.WED.getInputs())) {
                    dateNumber = SpecMealInputsEng.WED.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.THR.getInputs())) {
                    dateNumber = SpecMealInputsEng.THR.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.FRI.getInputs())) {
                    dateNumber = SpecMealInputsEng.FRI.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.SAT.getInputs())) {
                    dateNumber = SpecMealInputsEng.SAT.getInputValue();
                }
                else if(dateCustom.equals(SpecMealInputsEng.SUN.getInputs())) {
                    dateNumber = SpecMealInputsEng.SUN.getInputValue();
                }

                if(dateNumber == 0) {
                    day = 0;
                }
                else {
                    Integer dateDiff = dateNumber - currentDateNumber;
                    Integer dateDiffAbs = (dateDiff > 0) ? dateDiff : dateDiff * -1;
                    currentDateTime = (dateDiff > 0) ? currentDateTime.plusDays(dateDiffAbs) : currentDateTime.minusDays(dateDiffAbs);
                    year = currentDateTime.getYear();
                    month = currentDateTime.getMonth().getValue();
                    day = currentDateTime.getDayOfMonth();
                }

            }
        }

        Integer kindType = -1;
        if(bld.equals(SpecMealInputsEng.BREAKFAST.getInputs())) {
            kindType = SpecMealInputsEng.BREAKFAST.getInputValue();
        }
        else if(bld.equals(SpecMealInputsEng.LUNCH.getInputs())) {
            kindType = SpecMealInputsEng.LUNCH.getInputValue();
        }
        else if(bld.equals(SpecMealInputsEng.DINNER.getInputs())) {
            kindType = SpecMealInputsEng.DINNER.getInputValue();
        }

        if( (day < 1 && day > 31) || (kindType < 0 || kindType > 2)) {
            return Messages.NO_MEAL_ENG.getMessages();
        }

        String date = year + "-";
        date += String.format("%02d", month) + "-";
        date += String.format("%02d", day) + "";

        //형식이 잘못되면 Query 에서 NULL 을 반환한다.
        //if(!dateFormatValidation(date)) {
        //    return Messages.NO_MEAL_ENG.getMessages();
        //}

        Optional<Meal> result = sqlMealRepository.findByDate(Types.BLDG2_1ST.getType(), Types.LANG_ENG.getType(), kindType, date);

        if(result.isEmpty()) {
            //throw new IllegalStateException(Messages.EXIST_MEAL_ERROR.getMessages());
            return Messages.NO_MEAL_ENG.getMessages();
        }
        Meal meal = result.get();
        return meal.generateMenu();
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

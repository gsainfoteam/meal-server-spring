package com.example.helper.controller;
import com.example.helper.dto.MealWrapper;
import com.example.helper.entity.Meal;
import com.example.helper.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/meals", produces = "application/json;charset=UTF-8")
@Slf4j
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/all")
    public String hello() {
        return "Hello HELPERs. 초기 세팅 완료.";
    }

    @PostMapping("/create")
    public @ResponseBody String createMeal(MealWrapper mealWrapper) {
        // 여기 구현해주세 222
        Meal meal = mealWrapper.getMeal();
        // merge test
//        Meal saved = mealService.save(meal);

        return "saved"; // Responsebody 넣어서 보내는거 해보세요
    }

    @PostMapping("/kor")
    public @ResponseBody String readKorMeal() {
        // input : utc
        // output : 카톡 서버맞게 한국어식단 JSON으로
//        const responseBody = {
//                version: "2.0",
//                template: {
//            outputs: [
//            {
//                simpleText: {
//                    text: rows[0].menu
//                }
//            }
//          ]
//        }
//      };
//        res.status(200).send(responseBody);
        return "saved"; // Responsebody 넣어서 보내는거 해보세요
    }

    @PostMapping("/eng")
    public @ResponseBody String readEngMeal() {
        // input : utc
        // output : 카톡 서버맞게 영어식단 JSON으로

        return "saved"; // Responsebody 넣어서 보내는거 해보세요
    }

    @PostMapping("/speckor")
    public @ResponseBody String readSpecKorMeal() {
        // input : 날짜요일내일 + 아점저 + 1/2학
        // output : 해당 식단 찾아 카톡 서버맞게 JSON으로

        return "saved"; // Responsebody 넣어서 보내는거 해보세요
    }

    @PostMapping("/speceng")
    public @ResponseBody String readSpecEngMeal() {
        // input : 날짜요일내일 + 아점저 + 1/2학
        // output : 해당 식단 찾아 카톡 서버맞게 JSON으로

        return "saved"; // Responsebody 넣어서 보내는거 해보세요
    }
}

package com.example.helper.constant;

public enum Types {
    LANG_KOR(0),
    LANG_ENG(1),
    BLDG1_1ST(0),
    BLDG1_2ND(1),
    BLDG2_1ST(2),
    KIND_BREAKFAST(0),
    KIND_LUNCH(1),
    KIND_DINNER(2),
    KIND_LUNCH_CORNER(3);

    private Integer type;

    Types(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}

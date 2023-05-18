package com.avocado.product.config.enums;

public enum PersonalColor {
    spring_warm_light,
    spring_warm_bright,
    summer_cool_light,
    summer_cool_bright,
    summer_cool_mute,
    autumn_warm_mute,
    autumn_warm_strong,
    autumn_warm_deep,
    winter_cool_bright,
    winter_cool_deep,
    ;

    private static final PersonalColor[] personalColors = PersonalColor.values();
    public static String getPersonalColor(Byte index) {
        return personalColors[index].name();
    }
}

package com.avocado.product.config.enums;

public enum MBTI {
    INFJ,
    INFP,
    INTJ,
    INTP,
    ISFJ,
    ISTP,
    ENFJ,
    ENFP,
    ENTJ,
    ENTP,
    ESFJ,
    ESFP,
    ESTJ,
    ESTP,
    ;

    private static final MBTI[] MBTIs = MBTI.values();
    public static String getMBTI(Byte index) {
        return MBTIs[index].name();
    }
}

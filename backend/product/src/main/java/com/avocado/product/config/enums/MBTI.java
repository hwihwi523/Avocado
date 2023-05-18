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
        if (index == null)
            return null;
        return MBTIs[index].name();
    }
}

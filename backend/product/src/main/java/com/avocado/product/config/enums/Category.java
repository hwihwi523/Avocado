package com.avocado.product.config.enums;

public enum Category {
    Topwear,
    Bottomwear,
    Dress,
    Footwear,
    Bags,
    Accessories,
    ;

    private static final Category[] categories = Category.values();
    public static String getCategory(Short index) {
        return categories[index].name();
    }
}

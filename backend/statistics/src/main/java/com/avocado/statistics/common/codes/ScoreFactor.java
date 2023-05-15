package com.avocado.statistics.common.codes;

import org.springframework.stereotype.Component;

@Component
public class ScoreFactor {

    public final int VIEW = 1;
    public final int CLICK = 2;
    public final int LIKE = 5;
    public final int CART = 7;
    public final int PAY = 20;
}

package com.avocado.statistics.common.utils;

import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.db.redis.repository.CategoryType;
import org.springframework.stereotype.Component;

@Component
public class CategoryTypeUtil {

    public int getVarSize(CategoryType cType) {
        switch(cType) {
            case AGE_GENDER:
                return 14;
            case MBTI:
                return 16;
            case PERSONAL_COLOR:
                return 10;
            default:
                throw new BaseException(ResponseCode.INVALID_VALUE);
        }
    }
}

package com.avocado.statistics.common.utils;

import com.avocado.statistics.api.dto.GenderAgeGroup;
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

    // 10대 : 0, 1 / 20대 : 2, 3 / 30대 : 4, 5 / 40대 : 6, 7
    public int getIndexOfGenderAgeGroup(int ageGroup, String gender) {
        int index = (ageGroup / 10) - 1;
        if (gender.equals("M")) {
            index++;
        }
        return index;
    }

    public GenderAgeGroup getGenderAgeGroupFromIndex(int index) {
        int ageGroup = (index / 2 + 1) * 10;
        String gender;
        if (index % 2 == 0) {
            gender = "F";
        } else {
            gender = "M";
        }
        return new GenderAgeGroup(gender, ageGroup);
    }
}

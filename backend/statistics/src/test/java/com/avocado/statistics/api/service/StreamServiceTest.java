package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

@DisplayName("StreamService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StreamServiceTest {

    @Autowired StreamService streamService;

    String userId0 = "24E258AF-5B31-4BF1-8268-8F0EFACB1F04"; // 내 UUID..
    String userId1 = "4E7E7980-F3AB-11ED-8C69-66C892933BB2"; // 40M ESTJ autumn_warm_strong
    String userId2 = "4FF86D6F-F3AD-11ED-8C69-66C892933BB2"; // 30M ESFJ autumn_warm_deep
    String userId3 = "526ED335-F3AE-11ED-8C69-66C892933BB2"; // 10F INFP spring_warm_light
    String userId4 = "55D60A02-F3B2-11ED-8C69-66C892933BB2"; // 30F INTP summer_cool_bright
    String userId5 = "6AD3863B-F3B0-11ED-8C69-66C892933BB2"; // 40F ENFP autumn_warm_mute
    String userId6 = "B39228A9-F3AF-11ED-8C69-66C892933BB2"; // 10M ENFJ winter_cool_deep
    String userId7 = "6285E7A4-F082-11ED-B280-D606922A228C"; // 20M INFP winter_cool_bright
    String userId8 = "62F552F6-F082-11ED-B280-D606922A228C"; // 20F ISFJ spring_warm_bright

    String[] userIds = {userId1, userId2, userId3, userId4, userId5, userId6, userId7, userId8};

    long[] merchandiseIds = {4858, 4854, 29985, 30369, 18092, 12492, 28979, 30028, 17194, 15326, 15346, 15350, 16950, 16931, 22378, 30280, 30242, 25689, 30933, 29824, 23791,31865};

    @Test
    @DisplayName("실제 존재하는 광고 DTO INSERT 테스트")
    public void real_insert_adv_dto() {
        ActionType[] values = {ActionType.AD_CLICK, ActionType.AD_VIEW, ActionType.AD_PAYMENT};
        for (long id: merchandiseIds) {
            int size = new Random().nextInt(10) + 5;
            for (int i = 0; i < size; i++) {
                Result result = new Result(userId0, values[new Random().nextInt(3)]);
                streamService.consumeResult(result, id);
            }
        }
    }


    @Test
    @DisplayName("점수 DTO INSERT 테스트")
    public void insert_score_dto() {
        ActionType[] values = {ActionType.CART, ActionType.CLICK, ActionType.LIKE, ActionType.PAYMENT, ActionType.VIEW};
        for(long id = 0; id < 20; id++) {
            int size = new Random().nextInt(10) + 1;
            for (int i = 0; i < size; i++) {
                Result result = new Result(userId0, values[new Random().nextInt(values.length)]);
                streamService.consumeResult(result, id);
            }
        }
    }

    @Test
    @DisplayName("1번 상품에 더미데이터 넣기")
    public void insert_score_merchandiseId_1() {
        for (String userId: userIds) {
            int size = new Random().nextInt(10) + 1;
            for (int i = 0; i < size; i++) {
                Result result = new Result(userId, ActionType.PAYMENT);
                streamService.consumeResult(result, 1L);
            }
        }
    }
}

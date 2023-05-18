package com.avocado.statistics.api.service;

import com.avocado.statistics.db.redis.repository.ScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("추천용 더미 데이터 넣기")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecommendDummyDataTest {

    @Autowired ScoreRepository scoreRepository;
    @Autowired StreamService streamService;

    String userId1 = "4E7E7980-F3AB-11ED-8C69-66C892933BB2"; // 40M ESTJ autumn_warm_strong
    String userId2 = "4FF86D6F-F3AD-11ED-8C69-66C892933BB2"; // 30M ESFJ autumn_warm_deep
    String userId3 = "526ED335-F3AE-11ED-8C69-66C892933BB2"; // 10F INFP spring_warm_light
    String userId4 = "55D60A02-F3B2-11ED-8C69-66C892933BB2"; // 30F INTP summer_cool_bright
    String userId5 = "6AD3863B-F3B0-11ED-8C69-66C892933BB2"; // 40F ENFP autumn_warm_mute
    String userId6 = "B39228A9-F3AF-11ED-8C69-66C892933BB2"; // 10M ENFJ winter_cool_deep
    String userId7 = "6285E7A4-F082-11ED-B280-D606922A228C"; // 20M INFP winter_cool_bright
    String userId8 = "62F552F6-F082-11ED-B280-D606922A228C"; // 20F ISFJ spring_warm_bright

    String[] userIds = { userId1, userId2, userId3, userId4, userId5, userId6, userId7, userId8 };

    @Test
    public void 점수_초기화() {
        scoreRepository.deleteAll();

    }

//    @Test
//    public void

}

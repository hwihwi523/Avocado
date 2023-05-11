package com.avocado.statistics.api.service;

import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.db.mysql.repository.ConsumerRepository;
import com.avocado.statistics.db.mysql.entity.Consumer;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import com.avocado.statistics.kafka.dto.Result;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamService {

    private final ScoreRepository scoreRepository;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;
    private final ConsumerRepository consumerRepository;
    private final AdvertiseCountRepository advertiseCountRepository;

    public void consumeResult(Result result) {
        Long merchandiseId = result.getMerchandiseId();
        Type resType = result.getType();

        // 점수가 저장된 merchandise 로 처리하기
        merchandiseIdSetRepository.setUse(merchandiseId);

        // 광고 처리하기
        if (resType.equals(Type.AD_CLICK) || resType.equals(Type.AD_VIEW) || resType.equals(Type.AD_PAYMENT)) {
            advertiseCountRepository.save(resType, merchandiseId);
        }
        // 점수 저장하기
        else {
            saveScore(result);
        }
    }
    
    /**
     * 점수 저장 과정
     * @param result
     */
    private void saveScore(Result result) {
        UUID consumerId = UUID.fromString(result.getUserId());
        Type resType = result.getType();
        Long merchandiseId = result.getMerchandiseId();

        Optional<Consumer> consumerO = consumerRepository.getById(consumerId);
        // 없는 소비자면 에러 내기
        if (consumerO.isEmpty()) {
            throw new BaseException(ResponseCode.INVALID_VALUE);
        }

        Consumer consumer = consumerO.get();
        
        // age-gender score 저장하기
        Integer age = consumer.getAge();
        String gender = consumer.getGender();
        if (age != null && gender != null) {
            int index = (age / 10) -1; // 10대 -> 0, 1 / 20대 -> 2, 3 / 30대 -> 4, 5 /
            if (gender.equals("M")) {
                index++;
            }
            scoreRepository.save(CategoryType.AGE_GENDER, resType, merchandiseId, index);
        }
        
        // mbti score 저장하기
        Integer mbtiId = consumer.getMbtiId();
        if (mbtiId != null) {
            scoreRepository.save(CategoryType.MBTI, resType, merchandiseId, mbtiId);
        }
        
        // personal color score 저장하기
        Integer personalColorId = consumer.getPersonalColorId();
        if (personalColorId != null) {
            scoreRepository.save(CategoryType.PERSONAL_COLOR, resType, merchandiseId, personalColorId);
        }
    }
}

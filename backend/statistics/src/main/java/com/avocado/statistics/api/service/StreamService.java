package com.avocado.statistics.api.service;

import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import com.avocado.statistics.kafka.dto.Result;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final ScoreRepository scoreRepository;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;

    public void consumeResult(Result result) {
        UUID consumerId = UUID.fromString(result.getUserId());
        Long merchandiseId = result.getMerchandiseId();
        Type resType = result.getType();

        switch(resType) {
            case VIEW:


        }
    }

    private void saveScore(CategoryType cType, Long merchandiseId) {


    }


}

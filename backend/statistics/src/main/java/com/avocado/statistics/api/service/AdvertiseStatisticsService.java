package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.AdStatus;
import com.avocado.Status;
import com.avocado.statistics.common.utils.DateUtil;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import com.avocado.statistics.kafka.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdvertiseStatisticsService {

    private final AdvertiseCountRepository advertiseCountRepository;
    private final DateUtil dateUtil;
    private final KafkaProducer kafkaProducer;

    public void sendAdvertiseInfo() {
        String nowDate = dateUtil.getNowDate();
        List<Status> statusList = new ArrayList<>();

        Map<Long, Integer> viewMap = advertiseCountRepository.getMap(ActionType.AD_VIEW, nowDate);
        Map<Long, Integer> clickMap = advertiseCountRepository.getMap(ActionType.AD_CLICK, nowDate);
        Map<Long, Integer> payMap = advertiseCountRepository.getMap(ActionType.AD_PAYMENT, nowDate);


        Set<Long> merchandiseIds = new HashSet<>();
        merchandiseIds.addAll(viewMap.keySet());
        merchandiseIds.addAll(clickMap.keySet());
        merchandiseIds.addAll(payMap.keySet());

        for (Long merchandiseId: merchandiseIds) {
            Integer exposureCnt = viewMap.getOrDefault(merchandiseId, 0);
            Integer clickCnt = clickMap.getOrDefault(merchandiseId, 0);
            Integer quantity = payMap.getOrDefault(merchandiseId, 0);

            Status status = Status.newBuilder()
                    .setMerchandiseId(merchandiseId)
                    .setExposureCnt(exposureCnt)
                    .setClickCnt(clickCnt)
                    .setQuantity(quantity)
                    .build();
            statusList.add(status);
        }
        
        // Kafka 로 보내기 (to commercial Server)
        AdStatus adStatus = AdStatus.newBuilder()
                .setStatusList(statusList)
                .build();
        kafkaProducer.sendAdStatus(adStatus);
    }



}

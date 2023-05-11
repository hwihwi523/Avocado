package com.avocado.statistics.api.service;

import com.avocado.statistics.common.utils.DateUtil;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import com.avocado.statistics.kafka.dto.AdStatus;
import com.avocado.statistics.kafka.dto.Status;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdvertiseStatisticsService {

    private final AdvertiseCountRepository advertiseCountRepository;
    private final DateUtil dateUtil;


    public void sendAdvertiseInfo() {
        String nowDate = dateUtil.getNowDate();
        List<Status> statusList = new ArrayList<>();

        Map<Long, Integer> viewMap = advertiseCountRepository.getMap(Type.AD_VIEW, nowDate);
        Map<Long, Integer> clickMap = advertiseCountRepository.getMap(Type.AD_CLICK, nowDate);
        Map<Long, Integer> payMap = advertiseCountRepository.getMap(Type.AD_PAYMENT, nowDate);


        Set<Long> merchandiseIds = new HashSet<>();
        merchandiseIds.addAll(viewMap.keySet());
        merchandiseIds.addAll(clickMap.keySet());
        merchandiseIds.addAll(payMap.keySet());

        for (Long merchandiseId: merchandiseIds) {
            Integer exposureCnt = viewMap.getOrDefault(merchandiseId, 0);
            Integer clickCnt = clickMap.getOrDefault(merchandiseId, 0);
            Integer quantity = payMap.getOrDefault(merchandiseId, 0);

            Status status = Status.builder()
                    .merchandiseId(merchandiseId)
                    .exposureCnt(exposureCnt)
                    .clickCnt(clickCnt)
                    .quantity(quantity).build();
            statusList.add(status);
        }
        
        // Kafka 로 보내기 (to commercial Server)
        AdStatus adStatus = AdStatus.builder()
                .date(nowDate)
                .statusList(statusList)
                .build();
        System.out.println(adStatus);
    }



}

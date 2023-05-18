package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.AdStatus;
import com.avocado.Status;
import com.avocado.statistics.common.utils.DateUtil;
import com.avocado.statistics.db.mysql.repository.mybatis.MerchandiseRepository;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import com.avocado.statistics.kafka.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdvertiseStatisticsService {

    private final AdvertiseCountRepository advertiseCountRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final DateUtil dateUtil;
    private final KafkaProducer kafkaProducer;
    
    // 매일 밤 12시 10분에 처리
    @Scheduled(cron = "0 10 0 * * *", zone = "Asia/Seoul")
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
            Optional<Integer> discountedPriceO = merchandiseRepository.getDiscountedPrice(merchandiseId);

            if (discountedPriceO.isEmpty()) {
                continue;
            }

            Status status = Status.newBuilder()
                    .setMerchandiseId(merchandiseId)
                    .setExposureCnt(exposureCnt)
                    .setClickCnt(clickCnt)
                    .setQuantity(quantity)
                    .setAmount(discountedPriceO.get() * quantity)
                    .build();
            statusList.add(status);
        }
        
        // Kafka 로 보내기 (to commercial Server)
        AdStatus adStatus = AdStatus.newBuilder()
                .setStatusList(statusList)
                .build();
        System.out.println(adStatus);
        kafkaProducer.sendAdStatus(adStatus);
    }



}

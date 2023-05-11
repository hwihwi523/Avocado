package com.avocado.statistics.api.service;

import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertiseStatisticsService {

    private final AdvertiseCountRepository advertiseCountRepository;



}

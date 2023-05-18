package com.avocado.commercial.Repository;

import com.avocado.commercial.Entity.CommercialStatistic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommercialStatisticRepository extends JpaRepository<CommercialStatistic, Long> {

//    .clickCnt(status.getClickCnt())
//            .exposureCnt(status.getExposureCnt())
//            .quantity(status.getQuantity())
//            .date(epoch.plusDays(today).toString())
//            .purchaseAmount(status.getAmount())
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO commercial_statistic (exposure_cnt,click_cnt,purchase_amount,quantity,commercial_id,`date`)\n" +
            "values(:exposure_cnt, :click_cnt,:purchase_amount,:quantity," +
            "(select id from commercial where merchandise_id = :merchandiseId limit 1),:date)")
    void saveByMerchandiseId(long merchandiseId, long exposure_cnt, long click_cnt, long purchase_amount, long quantity, String date);


    List<CommercialStatistic> findByCommercialId(long commercialId, Sort sort);

    List<CommercialStatistic> findByCommercialId(long commercialId);

    void deleteByDate(String date);
}

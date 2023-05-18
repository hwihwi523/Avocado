package com.avocado.commercial.Repository;

import com.avocado.commercial.Entity.CommercialStatistic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommercialStatisticRepository extends JpaRepository<CommercialStatistic, Long> {

    @Query(nativeQuery = true, value = "INSERT INTO commercial_statistic (exposure_cnt,click_cnt,purchase_amount,quantity,commercial_id,`date`)\n" +
            "values(:commercialStatistic.exposureCnt,:commercialStatistic.clickCnt,:commercialStatistic.purchase_amount,:commercialStatistic.quantity," +
            "(select id from commercial where merchandise_id = :merchandiseId limit 1),:commercialStatistic.date)")
    void saveByMerchandiseId(long merchandiseId, CommercialStatistic commercialStatistic);

    List<CommercialStatistic> findByCommercialId(long commercialId, Sort sort);
}

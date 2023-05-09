package com.avocado.commercial.Repository;

import com.avocado.commercial.Dto.response.Analysis;
import com.avocado.commercial.Dto.response.RegistedCommercial;
import com.avocado.commercial.Dto.response.item.Click;
import com.avocado.commercial.Dto.response.item.Exposure;
import com.avocado.commercial.Dto.response.item.Purchase;
import com.avocado.commercial.Entity.Commercial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommercialRepository extends Repository<Commercial,Long> {
    List<Commercial> findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender
            (int mbtiId, int age, int commercialTypeId, int personalColorId, char gender);
    List<Commercial> findByMbtiIdAndAgeAndCommercialTypeIdAndGender
            (int mbtiId, int age, int commercialTypeId, char gender);
    List<Commercial> findByAgeAndCommercialTypeIdAndPersonalColorIdAndGender
            (int age, int commercialTypeId, int personalColorId, char gender);
    List<Commercial> findByAgeAndCommercialTypeIdAndGender
            (int age, int commercialTypeId, char gender);
    List<Commercial> findByCommercialTypeId(int commercialTypeId);

    void save(Commercial commercial);

    List<Commercial> findByProviderId(UUID providerId);

    Commercial findById(long id);

    @Query(nativeQuery = true, value = "SELECT DATE(created_at) as date, COUNT(*) AS exposure_cnt\n" +
            "FROM commercial_exposure\n" +
            "WHERE commercial_id = :commercialId\n" +
            "GROUP BY DATE(created_at)")
    List<Exposure> countExposureByCommercialId(@Param("commercialId")int commercialId);


    @Query(nativeQuery = true, value = "SELECT DATE(created_at) as date, COUNT(*) AS click_cnt\n" +
            "    FROM click\n" +
            "WHERE merchandise_id = :merchandiseId" +
            "    GROUP BY DATE(created_at)")
    List<Click> countClickByMerchandiseId(@Param("merchandiseId")long merchandiseId);


    @Query(nativeQuery = true, value = "SELECT DATE(p.created_at) AS date, COUNT(m.id) AS quantity, SUM(m.price * m.quantity) AS purchase_amount\n" +
            "    FROM purchased_merchandise m, purchase p\n" +
            "    WHERE m.purchase_id = p.id AND m.merchandise_id = 1\n" +
            "    GROUP BY DATE(p.created_at)")
    List<Purchase> countPurchaseByMerchandiseId(@Param("merchandiseId")long merchandiseId);


    void deleteById(long commercial_id);
}

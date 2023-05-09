package com.avocado.commercial.Repository;

import com.avocado.commercial.Dto.response.Analysis;
import com.avocado.commercial.Dto.response.RegistedCommercial;
import com.avocado.commercial.Dto.response.item.Exposure;
import com.avocado.commercial.Entity.Commercial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

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

    @Query(nativeQuery = true, value = "SELECT DATE(created_at) as date, COUNT(*) AS exposure_cnt\n" +
            "FROM commercial_exposure\n" +
            "GROUP BY DATE(created_at)")
    List<Exposure> countExByMerchandiseIdGroupBy(int merchandise_id);




}

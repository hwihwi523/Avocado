package com.avocado.commercial.Repository;

import com.avocado.commercial.Entity.Commercial;
import org.springframework.data.repository.Repository;

import java.util.List;

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


}

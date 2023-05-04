package com.avocado.commercial.Repository;

import com.avocado.commercial.Entity.Commercial;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CommercialRepository extends Repository<Commercial,Long> {
    List<Commercial> findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender
            (int mbtiId, int age, int commercialTypeId, int personalColorId, char gender);
}

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

    @Query(nativeQuery = true, value = "select * from commercial \n" +
            "where commercial_type_id = :commercialTypeId AND (personal_color_id = :personalColorId OR mbti_id = :mbtiId OR age = :age OR gender = :gender)\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 5")
    List<Commercial> findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender
            (int mbtiId, int age, int commercialTypeId, int personalColorId, char gender);

    @Query(nativeQuery = true, value = "select * from commercial \n" +
            "where commercial_type_id = :commercialTypeId AND (mbti_id = :mbtiId OR age = :age OR gender = :gender)\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 5")
    List<Commercial> findByMbtiIdAndAgeAndCommercialTypeIdAndGender
            (int mbtiId, int age, int commercialTypeId, char gender);

    @Query(nativeQuery = true, value = "select * from commercial \n" +
            "where commercial_type_id = :commercialTypeId AND (personal_color_id = :personalColorId OR age = :age OR gender = :gender)\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 5")
    List<Commercial> findByAgeAndCommercialTypeIdAndPersonalColorIdAndGender
            (int age, int commercialTypeId, int personalColorId, char gender);

    @Query(nativeQuery = true, value = "select * from commercial \n" +
            "where commercial_type_id = :commercialTypeId AND (age = :age OR gender = :gender)\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 5")
    List<Commercial> findByAgeAndCommercialTypeIdAndGender
            (int age, int commercialTypeId, char gender);

    @Query(nativeQuery = true, value = "select * from commercial \n" +
            "where commercial_type_id = :commercialTypeId\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 5")
    List<Commercial> findByCommercialTypeId(int commercialTypeId);

    List<Commercial> findByProviderId(UUID providerId);

    void save(Commercial commercial);

    Commercial findById(long id);


    long deleteByIdAndProviderId(long commercial_id, UUID provider_id);
}

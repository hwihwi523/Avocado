package com.avocado.product.service;

import com.avocado.Merchandise;
import com.avocado.product.dto.query.MaxAgeGroupDTO;
import com.avocado.product.dto.query.MaxMbtiDTO;
import com.avocado.product.dto.query.MaxPersonalColorDTO;
import com.avocado.product.entity.Mbti;
import com.avocado.product.entity.PersonalColor;
import com.avocado.product.entity.Tag;
import com.avocado.product.repository.ScoreRepository;
import com.avocado.product.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    @PersistenceContext
    private final EntityManager em;
    private final ScoreRepository scoreRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void updateAll() {
        // 행동점수가 있는 모든 상품의 태그 구하기
        List<MaxMbtiDTO> mbtis = scoreRepository.findMbtis(null);
        List<MaxPersonalColorDTO> personalColors = scoreRepository.findPersonalColors(null);
        List<MaxAgeGroupDTO> ageGroups = scoreRepository.findAges(null);

        // 상품 ID 취합
        Set<Long> merchandiseIds = new HashSet<>();
        for (MaxMbtiDTO mbti : mbtis)
            merchandiseIds.add(mbti.getMerchandiseId());
        for (MaxPersonalColorDTO personalColor : personalColors)
            merchandiseIds.add(personalColor.getMerchandiseId());
        for (MaxAgeGroupDTO ageGroup : ageGroups)
            merchandiseIds.add(ageGroup.getMerchandiseId());

        // 행동점수를 갖는 모든 상품 조회
        List<Tag> tags = tagRepository.findAll(merchandiseIds);

        // 태그 수정을 위한 Map 생성
        Map<Long, Tag> indexOf = new HashMap<>();
        for (Tag tag : tags)
            indexOf.put(tag.getMerchandise().getId(), tag);

        // MaxTypeDTO 맞게 수정
        for (MaxMbtiDTO mbti : mbtis)
            indexOf.get(mbti.getMerchandiseId()).updateMbti(
                    em.getReference(Mbti.class, mbti.getMbtiId())
            );
        for (MaxPersonalColorDTO personalColor : personalColors)
            indexOf.get(personalColor.getMerchandiseId()).updatePersonalColor(
                    em.getReference(PersonalColor.class, personalColor.getPersonalColorId())
            );
        for (MaxAgeGroupDTO ageGroup : ageGroups)
            indexOf.get(ageGroup.getMerchandiseId()).updateAgeGroup(
                    ageGroup.getAgeGroup()
            );
    }

    @Transactional
    public void updateByPurchaseHistoryEvent(List<Merchandise> merchandises) {
        // 상품 ID 취합
        List<Long> merchandiseIds = new ArrayList<>();
        for (Merchandise merchandise : merchandises)
            merchandiseIds.add(merchandise.getMerchandiseId());

        // 행동점수가 있는 모든 상품의 태그 구하기
        List<MaxMbtiDTO> mbtis = scoreRepository.findMbtis(merchandiseIds);
        List<MaxPersonalColorDTO> personalColors = scoreRepository.findPersonalColors(merchandiseIds);
        List<MaxAgeGroupDTO> ageGroups = scoreRepository.findAges(merchandiseIds);

        // 행동점수를 갖는 모든 상품 조회
        List<Tag> tags = tagRepository.findAll(merchandiseIds);

        // 태그 수정을 위한 Map 생성
        Map<Long, Tag> indexOf = new HashMap<>();
        for (Tag tag : tags)
            indexOf.put(tag.getMerchandise().getId(), tag);

        // MaxTypeDTO 맞게 수정
        for (MaxMbtiDTO mbti : mbtis)
            indexOf.get(mbti.getMerchandiseId()).updateMbti(
                    em.getReference(Mbti.class, mbti.getMbtiId())
            );
        for (MaxPersonalColorDTO personalColor : personalColors)
            indexOf.get(personalColor.getMerchandiseId()).updatePersonalColor(
                    em.getReference(PersonalColor.class, personalColor.getPersonalColorId())
            );
        for (MaxAgeGroupDTO ageGroup : ageGroups)
            indexOf.get(ageGroup.getMerchandiseId()).updateAgeGroup(
                    ageGroup.getAgeGroup()
            );
    }
}

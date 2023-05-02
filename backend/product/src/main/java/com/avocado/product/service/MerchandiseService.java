package com.avocado.product.service;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.PageResp;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.repository.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;

    // 대표 퍼스널컬러, MBTI, 나이대 등 개인화 정보 조회를 위한 service
    private final ScoreService scoreService;

    @Transactional(readOnly = true)
    public PageResp showMerchandiseList_NoOffset(Short categoryId, String brandName,
                                               Long lastMerchandiseId, Integer size) {
        // DB 조회
        Page<SimpleMerchandiseDTO> result = merchandiseRepository
                .findByCategoryAndBrand_NoOffset(categoryId, brandName, lastMerchandiseId, PageRequest.ofSize(size));

        // DTO -> Response 변환
        List<SimpleMerchandiseResp> respContent = scoreService.appendPersonalInfo(
                result.getContent()
        );

        // 마지막으로 조회한 ID
        Long newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getMerchandise_id();

        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId);
    }

    // Offset 사용 버전.
//    @Transactional(readOnly = true)
//    public PageResp showMerchandiseList_Offset(Short categoryId, String brandName,
//                                               Integer page, Integer size) {
//        Page<SimpleMerchandiseDTO> result = merchandiseRepository
//                .findByCategoryAndBrand_Offset(categoryId, brandName, PageRequest.of(page, size));
//
//        // DTO -> Response 변환
//        List<SimpleMerchandiseResp> respContent = scoreService.appendPersonalInfo(
//                result.getContent()
//        );
//
//        // 마지막으로 조회한 ID
//        Long newLastMerchandiseId = respContent.isEmpty()
//                ? null
//                : respContent.get(respContent.size() - 1).getMerchandise_id();
//
//        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId);
//    }
}

package com.avocado.product.service;

import com.avocado.product.dto.query.DetailMerchandiseDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.DetailMerchandiseResp;
import com.avocado.product.dto.response.PageResp;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.repository.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;

    // 대표 퍼스널컬러, MBTI, 나이대 등 개인화 정보 조회를 위한 service
    private final ScoreService scoreService;

    /**
     * 상품 목록을 카테고리와 브랜드 이름으로 조회한 데이터를 제공하는 서비스
     * @param categoryId : 카테고리 ID
     * @param brandName : 브랜드 이름
     * @param lastMerchandiseId : 마지막으로 조회한 상품 ID
     * @param size : 한 번에 조회할 데이터의 개수
     * @return : 상품 목록
     */
    @Transactional(readOnly = true)
    public PageResp showMerchandiseList_NoOffset(Short categoryId, String brandName,
                                               Long lastMerchandiseId, Integer size) {
        // DB 조회
        Page<SimpleMerchandiseDTO> result = merchandiseRepository
                .findByCategoryAndBrand_NoOffset(categoryId, brandName, lastMerchandiseId, PageRequest.ofSize(size));

        // DTO -> Response 변환
        List<SimpleMerchandiseResp> respContent = scoreService.insertPersonalInfoIntoList(
                result.getContent()
        );

        // 마지막으로 조회한 ID
        Long newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getMerchandise_id();

        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId);
    }

    @Transactional(readOnly = true)
    public DetailMerchandiseResp showDetailMerchandise(Long merchandiseId) {
        // DB 조회
        DetailMerchandiseDTO queryContent = merchandiseRepository.findDetailMerchandise(merchandiseId);

        // 대표 퍼스널컬러, MBTI, 나이대 정보 부착
        DetailMerchandiseResp respContent = scoreService.insertPersonalInfo(queryContent);

        // 추가 이미지 조회 및 부착
        List<String> additionalImages = merchandiseRepository.findAdditionalImages(merchandiseId);
        respContent.updateImages(additionalImages);

        return respContent;
    }

    @Transactional(readOnly = true)
    public List<SimpleMerchandiseResp> showRecentMerchandises(UUID consumerId) {
        // DB 조회
        List<SimpleMerchandiseDTO> recentMerchandises = merchandiseRepository.findRecentMerchandises(consumerId);

        // 대표 퍼스널컬러, MBTI, 나이대 추가 + DTO -> Response 변환
        return scoreService.insertPersonalInfoIntoList(recentMerchandises);
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

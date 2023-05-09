package com.avocado.commercial.Service;

import com.avocado.commercial.Dto.request.CommercialReqDto;
import com.avocado.commercial.Dto.response.RegistedCommercial;
import com.avocado.commercial.Dto.response.item.Carousel;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import com.avocado.commercial.Dto.response.item.Exposure;
import com.avocado.commercial.Entity.Commercial;
import com.avocado.commercial.Repository.CommercialRepository;
import com.avocado.commercial.util.JwtUtil;
import com.avocado.commercial.util.UUIDUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.*;

@Service
// 리팩토링 무조건 필요할 듯
public class CommercialService {

    private CommercialRepository commercialRepository;
    private ImageService imageService;
    private JwtUtil jwtUtil;

    private final int TYPE_POPUP = 0;
    private final int TYPE_CAROUSEL = 1;

    @Autowired
    public CommercialService(CommercialRepository commercialRepository, ImageService imageService, JwtUtil jwtUtil){
        this.commercialRepository = commercialRepository;
        this.imageService = imageService;
        this.jwtUtil = jwtUtil;
    }

    // 리팩토링 해야할 듯
    public CommercialRespDto getCommercialExposure(int mbtiId, int age, int personalColorId, char gender){
        
        // 응답으로 넘길 광고 리스트
        List<Commercial> popupEntityList = null;
        List<Commercial> carouselEntityList = null;

        // 넘어온 상태 값에 따라 다른 리스트를 가져온다.
        if(gender == 'X'){
            popupEntityList = commercialRepository.findByCommercialTypeId(TYPE_POPUP);
            carouselEntityList = commercialRepository.findByCommercialTypeId(TYPE_CAROUSEL);
        }else {
            // 나이 재설정
            // 나이는 10대씩 나누어 준다(70대 이상은 70대로 한다)
            age = age >= 70 ? 70 : age / 10 * 10;
            if (mbtiId == -1) {
                if (personalColorId == -1) {
                    popupEntityList = commercialRepository.findByAgeAndCommercialTypeIdAndGender(age, TYPE_POPUP, gender);
                    carouselEntityList = commercialRepository.findByAgeAndCommercialTypeIdAndGender(age, TYPE_CAROUSEL, gender);
                } else {
                    popupEntityList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndGender(mbtiId, age, TYPE_POPUP, gender);
                    carouselEntityList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndGender(mbtiId, age, TYPE_CAROUSEL, gender);
                }
            } else if (mbtiId == -1) {
                popupEntityList = commercialRepository.findByAgeAndCommercialTypeIdAndPersonalColorIdAndGender(age, TYPE_POPUP, personalColorId, gender);
                carouselEntityList = commercialRepository.findByAgeAndCommercialTypeIdAndPersonalColorIdAndGender(age, TYPE_CAROUSEL, personalColorId, gender);
            } else {
                popupEntityList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender(mbtiId, age, TYPE_POPUP, personalColorId, gender);
                carouselEntityList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender(mbtiId, age, TYPE_CAROUSEL, personalColorId, gender);
            }
        }

        // 광고가 없으면 예외
//        if(commercialList.size() == 0){
//            throw new CommercialException(ErrorCode.COMMERCIAL_NOT_FOUND);
//        }

        // 응답으로 넘길 Dto
        CommercialRespDto commercialRespDto = new CommercialRespDto();
        List<Carousel> carouselList = new ArrayList<>();
        // 무작위의 광고를 보여주기 위한 랜덤객체
        Random random = new Random();

        // 광고를 하나 뽑아 popup으로 넣는다
        if(0 <popupEntityList.size()) {
            commercialRespDto.setPopup((popupEntityList.get(random.nextInt(popupEntityList.size())).toPopup()));
        }
        
        
        // 광고 수가 5보다 적으면 그대로 넘긴다.
        if(carouselEntityList.size() < 5) {
            for (Commercial commercial : carouselEntityList) {
                carouselList.add(commercial.toCarousel());
            }
            commercialRespDto.setCarousel_list(carouselList);
            return commercialRespDto;
        }


        // 광고 수가 5보다 크면 SET을 이용해 랜덥한 광고를 5개 선출
        Set<Commercial> commercialSet = new HashSet<>();
        while (carouselEntityList.size() < 5) {
            int randomNumber = random.nextInt(carouselEntityList.size());  // 0부터 리스트사이즈까지의 임의의 정수 생성
            commercialSet.add(carouselEntityList.get(randomNumber));
        }
        for (Commercial commercial : commercialSet) {
            carouselList.add(commercial.toCarousel());
        }
        commercialRespDto.setCarousel_list(carouselList);


        return commercialRespDto;
    }


    public List<Exposure> getAnlyses(int merchandise_id){
        List<Exposure> list = null;
        List<Exposure> a = commercialRepository.countExByMerchandiseIdGroupBy(merchandise_id);
        System.out.println(a.get(0).getExposure_Cnt());

        return list;
    }

    public void saveCommercial(CommercialReqDto commercialReqDto, HttpServletRequest request){
        String str = imageService.createCommercialImages(commercialReqDto.getFile());
        UUID uuid = jwtUtil.getId(request);
        Commercial commercial = commercialReqDto.toEntity();
        commercial.setProviderId(uuid);

        commercialRepository.save(commercial);
    }

    public List<RegistedCommercial> getRegistedCommercial(HttpServletRequest request){
        UUID uuid = jwtUtil.getId(request);

        List<Commercial> commercialList = commercialRepository.findByProviderId(uuid);
        List<RegistedCommercial> list = null;

        return list;
    }

//    @PostMapping("/new")
//    @Operation(summary = "경매 게시글 생성 API", description = "경매 게시글을 작성한다.")
//    public ResponseEntity<?> createAuction(AuctionRegisterReq req, HttpServletRequest request) {
//        log.debug("POST /auction request : {}", req);
//
//        // 제목 검증
//        if (req.getTitle().isBlank())
//            return ResponseEntity.status(400).body(BaseResponseBody.of("제목을 입력해주세요."));
//        // 시작가 검증
//        if (req.getOffer_price() == null || req.getOffer_price() < 0)
//            return ResponseEntity.status(400).body(BaseResponseBody.of("시작가를 0 이상 입력해주세요."));
//        // 경매 단위 검증
//        if (req.getPrice_size() == null || req.getPrice_size() < 1)
//            return ResponseEntity.status(400).body(BaseResponseBody.of("경매 단위를 1 이상 입력해주세요."));
//        // 종료 시간 검증
//        if (req.getEnd_at() == null)
//            return ResponseEntity.status(400).body(BaseResponseBody.of("종료 시간을 입력해주세요."));
//        // 파일 타입 검증
//        if (req.getFiles() != null) {  // 파일이 주어졌을 때만 검증
//            for (MultipartFile multipartFile : req.getFiles())
//                if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/"))
//                    return ResponseEntity.status(400).body(BaseResponseBody.of("이미지 파일만 등록해주세요."));
//        }
//
//        Long sellerId = jwtUtil.getUserId(request);
//        auctionService.createAuction(req, sellerId);
//
//        return ResponseEntity.status(201).body(BaseResponseBody.of("경매 게시글이 작성되었습니다."));
//    }
}

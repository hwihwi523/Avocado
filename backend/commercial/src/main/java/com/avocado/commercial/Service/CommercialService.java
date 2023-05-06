package com.avocado.commercial.Service;

import com.avocado.commercial.Dto.response.item.Carousel;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import com.avocado.commercial.Dto.response.item.Popup;
import com.avocado.commercial.Entity.Commercial;
import com.avocado.commercial.Exceptions.CommercialException;
import com.avocado.commercial.Exceptions.ErrorCode;
import com.avocado.commercial.Repository.CommercialRepository;
import net.bytebuddy.TypeCache;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
// 리팩토링 무조건 필요할 듯
public class CommercialService {

    private CommercialRepository commercialRepository;

    private final int TYPE_POPUP = 0;
    private final int TYPE_CAROUSEL = 1;

    public CommercialService(CommercialRepository commercialRepository){
        this.commercialRepository = commercialRepository;
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

}

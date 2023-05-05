package com.avocado.commercial.Service;

import com.avocado.commercial.Dto.response.item.Carousel;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import com.avocado.commercial.Dto.response.item.Popup;
import com.avocado.commercial.Entity.Commercial;
import com.avocado.commercial.Repository.CommercialRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommercialService {

    private CommercialRepository commercialRepository;

    public CommercialService(CommercialRepository commercialRepository){
        this.commercialRepository = commercialRepository;
    }

    // 리팩토링 해야할 듯
    public CommercialRespDto getCommercialExposure(int mbtiId, int age, int commercialTypeId, int personalColorId, char gender) throws Exception{

        // 나이 재설정
        // 나이는 10대씩 나누어 준다(70대 이상은 70대로 한다)
        age = age >= 70 ? 70 : age / 10 * 10;
        
        // 응답으로 넘길 광고 리스트
        List<Commercial> commercialList = null;

        // 넘어온 상태 값에 따라 다른 리스트를 가져온다.
        if(gender == -1){
            commercialRepository.findAll();
        }
        else if(mbtiId == -1){
            if(personalColorId == -1){
                commercialList = commercialRepository.findByAgeAndCommercialTypeIdAndGender(age,commercialTypeId,gender);
            }
            else{
                commercialList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndGender(mbtiId,age,commercialTypeId,gender);
            }
        }
        else if(mbtiId == -1){
            commercialList = commercialRepository.findByAgeAndCommercialTypeIdAndPersonalColorIdAndGender(age,commercialTypeId,personalColorId,gender);
        }
        else{
            commercialList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender(mbtiId,age,commercialTypeId,personalColorId,gender);
        }

        // 광고가 없으면 예외
        if(commercialList.size() == 0){
            throw new Exception();
        }

        // 응답으로 넘길 Dto
        CommercialRespDto commercialRespDto = new CommercialRespDto();
        List<Carousel> carouselList = new ArrayList<>();
        // 무작위의 광고를 보여주기 위한 랜덤객체
        Random random = new Random();

        // 광고를 하나 뽑아 popup으로 넣는다
        commercialRespDto.setPopup(((Popup)commercialList.get(random.nextInt(commercialList.size())).toPopup()));
        
        
        // 광고 수가 5보다 적으면 그대로 넘긴다.
        if(commercialList.size() < 5) {
            for (Commercial commercial : commercialList) {
                carouselList.add((Carousel) commercial.toCarousel());
            }
            commercialRespDto.setCarousel_list(carouselList);
            return commercialRespDto;
        }


        // 광고 수가 5보다 크면 SET을 이용해 랜덥한 광고를 5개 선출
        Set<Commercial> commercialSet = new HashSet<Commercial>();
        while (commercialList.size() < 5) {
            int randomNumber = random.nextInt(commercialList.size());  // 0부터 리스트사이즈까지의 임의의 정수 생성
            commercialSet.add(commercialList.get(randomNumber));
        }
        for (Commercial commercial : commercialSet) {
            carouselList.add((Carousel)commercial.toCarousel());
        }
        commercialRespDto.setCarousel_list(carouselList);


        return commercialRespDto;
    }

}

package com.avocado.commercial.Service;

import com.avocado.commercial.Dto.response.CommercialRespDto;
import com.avocado.commercial.Entity.Commercial;
import com.avocado.commercial.Repository.CommercialRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommercialService {

    private CommercialRepository commercialRepository;

    public CommercialService(CommercialRepository commercialRepository){
        this.commercialRepository = commercialRepository;
    }

    public List<CommercialRespDto> getCommercialExposure(int mbtiId, int age, int commercialTypeId, int personalColorId, char gender) throws Exception{
        List<Commercial> commercialList = commercialRepository.findByMbtiIdAndAgeAndCommercialTypeIdAndPersonalColorIdAndGender(mbtiId,age,commercialTypeId,personalColorId,gender);
        List<CommercialRespDto> commercialRespDtoList = new ArrayList<>();

        for(Commercial commercial : commercialList){
            commercialRespDtoList.add(commercial.toCommercialRespDto());
        }

        return commercialRespDtoList;
    }

}

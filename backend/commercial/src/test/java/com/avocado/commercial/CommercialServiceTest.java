package com.avocado.commercial;

import com.avocado.commercial.Dto.request.CommercialReqDto;
import com.avocado.commercial.Service.CommercialService;
import com.avocado.commercial.error.CommercialException;
import com.avocado.commercial.error.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommercialServiceTest {

    @Autowired
    private CommercialService commercialService;
    @Test
    public void getCommercialExposure(){
//        System.out.println(commercialService.getCommercialExposure(1,22,1,'M'));
    }


    public void checkCommercialRequest(CommercialReqDto commercialReqDto){
        if(commercialReqDto.getAge() < 0){
            throw new CommercialException(ErrorCode.BAD_AGE);
        }
        if(commercialReqDto.getGender() != 'M' && commercialReqDto.getGender() != 'F' ){
            throw new CommercialException(ErrorCode.BAD_GENDER_TYPE);
        }
        if(commercialReqDto.getMbti_id() < 0 || 15 < commercialReqDto.getMbti_id()){
            throw new CommercialException(ErrorCode.BAD_MBTI_ID);
        }
        if(commercialReqDto.getCommercial_type_id() < 0 || 1 < commercialReqDto.getCommercial_type_id()){
            throw new CommercialException(ErrorCode.BAD_COMMERCIAL_TYPE);
        }
        if(commercialReqDto.getPersonal_color_id() < 0 || 9 < commercialReqDto.getPersonal_color_id()){
            throw new CommercialException(ErrorCode.BAD_PERSONAL_COLOR);
        }
    }
    public void checkCommercialRequest(int age, char gender, int mbtiId, int personalColorId){
        if(age < -1){
            throw new CommercialException(ErrorCode.BAD_AGE);
        }
        if(gender != 'M' && gender != 'F' && gender != 'X'){
            throw new CommercialException(ErrorCode.BAD_GENDER_TYPE);
        }
        if(mbtiId < 0 || 15 < mbtiId){
            throw new CommercialException(ErrorCode.BAD_MBTI_ID);
        }
        if(personalColorId < 0 || 9 < personalColorId){
            throw new CommercialException(ErrorCode.BAD_PERSONAL_COLOR);
        }
    }
}

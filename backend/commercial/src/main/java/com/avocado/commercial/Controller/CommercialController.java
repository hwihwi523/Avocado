package com.avocado.commercial.Controller;

import com.avocado.commercial.Service.CommercialService;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
////ads?age={age}&gender={gender}mbti_id={mbti_id}&commercial_type_id={commercial_type_id}&personal_color_id={personal_color_id}
public class CommercialController {

    private CommercialService commercialService;

    @Autowired
    public CommercialController(CommercialService commercialService){
        this.commercialService = commercialService;
    }

    // 70대까지
    // 다양한 조건에서 되게 만들어야 함 (ex. personalColor가 없음)
    // 팝업 광고 1개, 캐러셀 광고 5개 합쳐서 보내자
    @GetMapping("/ads")
    public ResponseEntity<CommercialRespDto> exposeCommercial(
            @RequestParam(name = "mbti_id", defaultValue = "-1") int mbtiId, @RequestParam(name = "age", defaultValue = "-1") int age,
            @RequestParam(name = "personal_color_id", defaultValue = "-1") int personalColorId, @RequestParam(name = "gender", defaultValue = "X") char gender){
        System.out.println(personalColorId);
        CommercialRespDto commercialRespDto = null;
        
        // 예외 처리 환경 구성 필요
//        try{
            commercialRespDto = commercialService.getCommercialExposure(mbtiId, age, personalColorId, gender);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        
        // 응답 환경 구성 필요
        return new ResponseEntity<CommercialRespDto>(commercialRespDto, HttpStatus.OK);
    }


}

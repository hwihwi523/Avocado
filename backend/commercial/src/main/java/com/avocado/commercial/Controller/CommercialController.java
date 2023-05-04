package com.avocado.commercial.Controller;

import com.avocado.commercial.Service.CommercialService;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/ads")
    public ResponseEntity<CommercialRespDto> exposePopupCommercial(
            @RequestParam("mbti_id") int mbtiId,@RequestParam("age") int age,  @RequestParam("commercial_type_id") int commercialTypeId,
            @RequestParam("personal_color_id") int personalColorId, @RequestParam("gender") char gender){
        List<CommercialRespDto> commercialRespDtoList = null;
        try{
            commercialRespDtoList = commercialService.getCommercialExposure(mbtiId,age >=70 ? 70 : age/10*10,commercialTypeId,personalColorId,gender);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<CommercialRespDto>(commercialRespDtoList.get(0), HttpStatus.OK);
    }


}

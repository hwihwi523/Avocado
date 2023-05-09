package com.avocado.commercial.Controller;

import com.avocado.commercial.Dto.request.CommercialReqDto;
import com.avocado.commercial.Dto.response.RegistedCommercial;
import com.avocado.commercial.Dto.response.item.Exposure;
import com.avocado.commercial.Service.CommercialService;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

            commercialRespDto = commercialService.getCommercialExposure(mbtiId, age, personalColorId, gender);
        
        // 응답 환경 구성 필요
        return new ResponseEntity<CommercialRespDto>(commercialRespDto, HttpStatus.OK);
    }
    @GetMapping("/analyses/{merchandise_id}")
    public ResponseEntity<List<Exposure>> getAnalyses(@PathVariable("merchandise_id") int merchandiseId){
        List<Exposure> list = commercialService.getAnlyses(1);



        return new ResponseEntity<List<Exposure>>(list,HttpStatus.OK);
    }


    @PostMapping( "/ads")
    public ResponseEntity<String> registCommercial(CommercialReqDto commercial, HttpServletRequest request){

        commercialService.saveCommercial(commercial, request);
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @GetMapping("/ads/registed")
    public ResponseEntity<List<RegistedCommercial>> getRegistedCommercial(HttpServletRequest request){
        List<RegistedCommercial> list = commercialService.getRegistedCommercial(request);

        return new ResponseEntity<List<RegistedCommercial>>(list,HttpStatus.OK);
    }

}

package com.avocado.commercial.Controller;

import com.avocado.commercial.Dto.request.CommercialReqDto;
import com.avocado.commercial.Dto.response.Analysis;
import com.avocado.commercial.Dto.response.item.Exposure;
import com.avocado.commercial.Service.CommercialService;
import com.avocado.commercial.Dto.response.CommercialRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        commercialService.saveCommercial(commercial);

        return new ResponseEntity<String>("success",HttpStatus.CREATED);
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

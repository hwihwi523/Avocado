package com.avocado.community.api.service;

import com.avocado.community.api.request.PostStyleshotReq;
import com.avocado.community.api.response.MerchandiseResp;
import com.avocado.community.api.response.StyleshotResp;
import com.avocado.community.common.error.BaseException;
import com.avocado.community.common.error.ResponseCode;
import com.avocado.community.common.utils.JwtUtils;
import com.avocado.community.db.entity.Styleshot;
import com.avocado.community.db.repository.MerchandiseRepository;
import com.avocado.community.db.repository.StyleshotLikeRepository;
import com.avocado.community.db.repository.StyleshotRepository;
import com.avocado.community.db.repository.WearRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StyleshotService {

    private final StyleshotRepository styleshotRepository;
    private final StyleshotLikeRepository styleshotLikeRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final WearRepository wearRepository;
    private final ImageService imageService;
    private final JwtUtils jwtUtils;

    @Transactional
    public void addStyleshot(PostStyleshotReq req, Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        if (req.getPicture() == null || req.getPicture().length <= 0) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "이미지 파일을 등록해주세요.");
        }

        // 2. 파일 타입 검증
        if (!req.getPicture()[0].getContentType().startsWith("image/")) {
            log.info("content type: {}",req.getPicture()[0].getContentType());
            throw new BaseException(HttpStatus.BAD_REQUEST, "이미지 파일만 등록해주세요.");
        }

        String imgUrl = imageService.uploadImage(req.getPicture()[0], "styleshots");

        UUID consumerId = jwtUtils.getId(claims);
        
        // 3. 스냅샷 저장
        Styleshot styleshot = Styleshot.of(req, imgUrl, consumerId);
        styleshotRepository.save(styleshot);

        // 4. 입었던 옷들 저장
        for (long merchandiseId: req.getWears()) {
            wearRepository.save(styleshot.getId(), merchandiseId);
        }
    }

    @Transactional
    public void deleteStyleshot(long styleshotId, Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID consumerId = jwtUtils.getId(claims);
        Optional<Long> check = styleshotRepository.getByIdAndConsumerId(styleshotId, consumerId);

        // 본인이 쓴 글이 아니면 에러 처리
        if (check.isEmpty()) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        styleshotRepository.deleteById(styleshotId);
        wearRepository.deleteAllByStyleshotId(styleshotId);

    }

    public List<StyleshotResp> styleshotList() {
        List<StyleshotResp> respList = styleshotRepository.getAll();
        for (StyleshotResp resp: respList) {
            List<MerchandiseResp> wearList = merchandiseRepository.getWearById(resp.getId());
            resp.setWears(wearList);
        }

        return respList;
    }

    public StyleshotResp showStyleshotDetail(long styleshotId) {
        Optional<StyleshotResp> styleshotO = styleshotRepository.getById(styleshotId);
        if (styleshotO.isEmpty()) {
            throw new BaseException(ResponseCode.BAD_REQUEST);
        }
        List<MerchandiseResp> wearList = merchandiseRepository.getWearById(styleshotId);
        StyleshotResp resp = styleshotO.get();
        resp.setWears(wearList);
        return resp;
    }

    public List<StyleshotResp> myStyleshotList(Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID consumerId = jwtUtils.getId(claims);

        List<StyleshotResp> respList = styleshotRepository.getAllByConsumerId(consumerId);

        for (StyleshotResp resp: respList) {
            List<MerchandiseResp> wearList = merchandiseRepository.getWearById(resp.getId());
            resp.setWears(wearList);
        }
        return respList;

    }

    @Transactional
    public void like(long styleshotId, Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID consumerId = jwtUtils.getId(claims);
        styleshotLikeRepository.like(styleshotId, consumerId);

    }

    @Transactional
    public void unlike(long styleshotId, Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID consumerId = jwtUtils.getId(claims);
        styleshotLikeRepository.unlike(styleshotId, consumerId);

    }


}

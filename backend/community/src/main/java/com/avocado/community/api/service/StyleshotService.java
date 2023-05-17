package com.avocado.community.api.service;

import com.avocado.community.api.request.PostStyleshotReq;
import com.avocado.community.api.response.CountsResp;
import com.avocado.community.api.response.MerchandiseResp;
import com.avocado.community.api.response.StyleshotPagingResp;
import com.avocado.community.api.response.StyleshotResp;
import com.avocado.community.common.error.BaseException;
import com.avocado.community.common.error.ResponseCode;
import com.avocado.community.common.utils.JwtUtils;
import com.avocado.community.db.entity.Consumer;
import com.avocado.community.db.entity.Styleshot;
import com.avocado.community.db.repository.*;
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

    private final ConsumerRepository consumerRepository;
    private final StyleshotRepository styleshotRepository;
    private final StyleshotLikeRepository styleshotLikeRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final WearRepository wearRepository;
    private final ImageService imageService;
    private final JwtUtils jwtUtils;


    public CountsResp getCounts(Claims claims) {
        UUID consumerId = jwtUtils.getId(claims);
        int styleshotCnt = styleshotRepository.getStyleshotCnt(consumerId);
        int likeCnt = styleshotRepository.getLikeCnt(consumerId);
        return CountsResp.builder()
                .styleshotCnt(styleshotCnt)
                .likeCnt(likeCnt)
                .build();
    }

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

    public StyleshotPagingResp styleshotList(Long lastId, Integer resultSize, Claims claims) {
        UUID myId = null;
        if (claims != null) {
            myId = jwtUtils.getId(claims);
        }

        List<Styleshot> styleshotList;
        if (lastId == null) {
            styleshotList = styleshotRepository.getAllFirstPageable(resultSize);
        } else {
            styleshotList = styleshotRepository.getAllPageable(lastId, resultSize);
        }

        List<StyleshotResp> respList = new ArrayList<>();

        for (Styleshot styleshot: styleshotList) {
            StyleshotResp resp = getStyleshotResp(styleshot, myId);
            respList.add(resp);

        }
        int listSize = styleshotList.size();
        Long newLastId = null;

        if (listSize != 0) {
            newLastId = styleshotList.get(listSize - 1).getId();
        }

        boolean isLastPage = false;

        List<Styleshot> nextStyle = styleshotRepository.getAllPageable(newLastId, 1);
        if (nextStyle.isEmpty()) {
            isLastPage = true;
        }

        StyleshotPagingResp resp = StyleshotPagingResp.builder()
                .styleshotList(respList)
                .lastId(newLastId)
                .isLastPage(isLastPage)
                .build();

        return resp;
    }

    public StyleshotResp showStyleshotDetail(long styleshotId, Claims claims) {

        Optional<Styleshot> styleshotO = styleshotRepository.getById(styleshotId);
        if (styleshotO.isEmpty()) {
            throw new BaseException(ResponseCode.BAD_REQUEST);
        }
        Styleshot styleshot = styleshotO.get();

        UUID myId = jwtUtils.getId(claims);

        return getStyleshotResp(styleshot, myId);
    }

    public List<StyleshotResp> myStyleshotList(Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID myId = jwtUtils.getId(claims);

        List<Styleshot> styleshotList = styleshotRepository.getAllByConsumerId(myId);

        List<StyleshotResp> respList = new ArrayList<>();

        for (Styleshot styleshot: styleshotList) {
            StyleshotResp resp = getStyleshotResp(styleshot, myId);
            respList.add(resp);
        }
        return respList;
    }

    private StyleshotResp getStyleshotResp(Styleshot styleshot, UUID myId) {
        StyleshotResp resp = new StyleshotResp(styleshot);

        // styleshot 게시자 정보 추가
        Optional<Consumer> consumerO = consumerRepository.findById(styleshot.getConsumerId());
        if (consumerO.isEmpty()) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "DB 오류");
        }
        resp.updateUserInfo(consumerO.get());

        // 착용 정보 추가
        List<MerchandiseResp> wearList = merchandiseRepository.getWearById(styleshot.getId());
        resp.setWears(wearList);

        // 총 좋아요 수 추가
        resp.setTotalLikes(styleshotLikeRepository.countTotal(resp.getId()));

        // 내 스냅샷인지 여부 추가
        if (styleshot.getConsumerId().equals(myId)) {
            resp.setMyStyleshot(true);
        }

        // 내가 좋아요를 했는지 추가
        if (myId != null) {
            int check = styleshotLikeRepository.checkExist(styleshot.getId(), myId);
            if (check > 0) {
                resp.setILiked(true);
            }
        }

        return resp;
    }


    @Transactional
    public void like(long styleshotId, Claims claims) {
        // 1. consumer 권한이 아닌 경우 에러 발생
        if (!jwtUtils.getType(claims).equals("consumer")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        int check = styleshotLikeRepository.checkExist(styleshotId, jwtUtils.getId(claims));

        if (check != 0) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.");
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

        int check = styleshotLikeRepository.checkExist(styleshotId, jwtUtils.getId(claims));

        if (check == 0) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "좋아요를 한 적이 없습니다.");
        }

        UUID consumerId = jwtUtils.getId(claims);
        styleshotLikeRepository.unlike(styleshotId, consumerId);

    }


}

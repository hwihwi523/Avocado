package com.avocado.community.api.controller;

import com.avocado.community.api.request.PostStyleshotReq;
import com.avocado.community.api.service.StyleshotService;
import com.avocado.community.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("styleshots")
@RequiredArgsConstructor
public class StyleshotController {

    private final JwtUtils jwtUtils;
    private final StyleshotService styleshotService;

    @GetMapping
    public ResponseEntity<?> getStyleshotList(@RequestParam @Nullable Long lastId, @RequestParam(defaultValue = "12") Integer size) {
        log.info("get all styleshot list");

        return ResponseEntity.ok(styleshotService.styleshotList(lastId, size));
    }

    @GetMapping("{styleshotId}")
    public ResponseEntity<?> getStyleshotDetail(@PathVariable long styleshotId) {
        log.info("get detail - styleshotId : {}", styleshotId);
        return ResponseEntity.ok(styleshotService.showStyleshotDetail(styleshotId));
    }

    @PostMapping
    public ResponseEntity<?> postStyleshot(PostStyleshotReq req, HttpServletRequest request) {
        log.info("request to post styleshot - req: {}", req);
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.addStyleshot(req, claims);
        resMap.put("status", 200);
        resMap.put("msg", "스냅샷_등록_성공");
        return ResponseEntity.ok(resMap);
    }

    @DeleteMapping("{styleshotId}")
    public ResponseEntity<?> deleteStyleShot(@PathVariable long styleshotId, HttpServletRequest request) {
        log.info("styleshot delete - styleshotId : {}", styleshotId);
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.deleteStyleshot(styleshotId, claims);
        resMap.put("status", 200);
        resMap.put("msg", "스냅샷_삭제_성공");
        return ResponseEntity.ok(resMap);
    }

    @PostMapping("{styleshotId}/like")
    public ResponseEntity<?> likeStyleshot(@PathVariable long styleshotId, HttpServletRequest request) {
        log.info("styleshot like - styleshotId : {}", styleshotId);
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.like(styleshotId, claims);
        resMap.put("status", 200);
        resMap.put("msg", "좋아요_성공");
        return ResponseEntity.ok(resMap);
    }

    @PostMapping("{styleshotId}/unlike")
    public ResponseEntity<?> unlikeStyleshot(@PathVariable long styleshotId, HttpServletRequest request) {
        log.info("styleshot unlike - styleshotId : {}", styleshotId);
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.unlike(styleshotId, claims);
        resMap.put("status", 200);
        resMap.put("msg", "좋아요_취소_성공");
        return ResponseEntity.ok(resMap);
    }

}

package com.avocado.community.api.controller;

import com.avocado.community.api.request.PostStyleshotReq;
import com.avocado.community.api.service.StyleshotService;
import com.avocado.community.common.error.ResponseCode;
import com.avocado.community.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("styleshots")
@RequiredArgsConstructor
public class StyleshotController {

    private final JwtUtils jwtUtils;
    private final StyleshotService styleshotService;

    @GetMapping
    public ResponseEntity<?> getStyleshotList() {
        return ResponseEntity.ok(styleshotService.styleshotList());
    }

    @GetMapping("{styleshotId}")
    public ResponseEntity<?> getStyleshotDetail(@PathVariable long styleshotId) {
        return ResponseEntity.ok(styleshotService.showStyleshotDetail(styleshotId));
    }

    @PostMapping
    public ResponseEntity<?> postStyleshot(@RequestBody PostStyleshotReq req, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.addStyleshot(req, claims);
        return ResponseEntity.ok(resMap);
    }

    @DeleteMapping("{styleshotId}")
    public ResponseEntity<?> deleteStyleShot(@PathVariable long styleshotId, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<>();
        Claims claims = jwtUtils.getClaims(request);
        styleshotService.deleteStyleshot(styleshotId, claims);
        return ResponseEntity.ok(resMap);
    }

}

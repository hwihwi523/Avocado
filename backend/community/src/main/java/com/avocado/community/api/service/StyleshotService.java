package com.avocado.community.api.service;

import com.avocado.community.api.request.PostStyleshotReq;
import com.avocado.community.api.response.StyleshotResp;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleshotService {


    public void addStyleshot(PostStyleshotReq req, Claims claims) {

    }

    public void deleteStyleshot(long styleshotId, Claims claims) {

    }

    public List<StyleshotResp> styleshotList() {
        return null;
    }

    public StyleshotResp showStyleshotDetail(long styleshotId) {
        StyleshotResp resp = new StyleshotResp();
        return resp;
    }


}

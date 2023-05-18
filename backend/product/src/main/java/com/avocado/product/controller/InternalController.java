package com.avocado.product.controller;

import com.avocado.product.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalController {
    private final TagService tagService;

    @PostMapping("/internal/update-all-tag")
    public String updateAllTag() {
        tagService.updateAll();
        return "종료~";
    }
}

package com.avocado.commercial.Dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommercialReqDto {
    private int merchandise_id;
    private int mbti_id;
    private int personal_color_id;
    private int commercial_type_id;
    private int age;
    private char gender;
    private MultipartFile file;
}

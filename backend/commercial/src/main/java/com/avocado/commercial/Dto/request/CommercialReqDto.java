package com.avocado.commercial.Dto.request;

import com.avocado.commercial.Entity.Commercial;
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
    private String merchandise_name;
    private int mbti_id;
    private int personal_color_id;
    private int commercial_type_id;
    private int age;
    private char gender;
    private MultipartFile[] file;

    public Commercial toEntity(){
        Commercial commercial = Commercial.builder()
                .commercialTypeId(this.commercial_type_id)
                .age(this.age)
                .gender(this.gender)
                .personalColorId(this.personal_color_id)
                .merchandiseId(this.merchandise_id)
                .mbtiId(this.mbti_id)
                .merchandiseName(this.merchandise_name)
                .build();
        return commercial;
    }
}

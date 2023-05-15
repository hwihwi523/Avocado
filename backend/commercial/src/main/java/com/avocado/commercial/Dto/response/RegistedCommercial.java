package com.avocado.commercial.Dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegistedCommercial {

    private long id;

    private int age;

    private char gender;

    private String imgurl;

    private String merchandiseName;

    private int commercialTypeId;

    private int mbtiId;

    private int personalColorId;

    private LocalDateTime createdAt;

    private long merchandiseId;

    private UUID providerId;
}

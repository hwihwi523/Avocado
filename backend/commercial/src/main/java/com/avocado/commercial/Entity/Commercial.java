package com.avocado.commercial.Entity;

import com.avocado.commercial.Dto.response.item.Carousel;
import com.avocado.commercial.Dto.response.item.Popup;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name="commercial")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Commercial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @Column(nullable = false,columnDefinition = "SMALLINT")
    private int age;

    @Column(nullable=false)
    private char gender;

    @Column(nullable = false)
    private String imgurl;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private int commercialTypeId;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private int mbtiId;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private int personalColorId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private long merchandiseId;

    public Carousel toCarousel(){
        Carousel carousel = Carousel.builder()
                .imgurl(this.imgurl)
                .merchandise_id(this.merchandiseId)
                .build();
        return carousel;
    }
    public Popup toPopup(){
        Popup popup = Popup.builder()
                .imgurl(this.imgurl)
                .merchandise_id(this.merchandiseId)
                .build();
        return popup;
    }
}
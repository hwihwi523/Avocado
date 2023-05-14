package com.avocado.community.api.response;

import com.avocado.community.db.entity.Consumer;
import com.avocado.community.db.entity.Styleshot;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StyleshotResp {
    boolean myStyleshot;
    boolean iLiked;
    UserInfo userInfo;
    long id;
    String content;
    String pictureUrl;
    int totalLikes;
    LocalDateTime createdAt;
    List<MerchandiseResp> wears;

    public StyleshotResp(Styleshot styleshot) {
        this.id = styleshot.getId();
        this.content = styleshot.getContent();
        this.pictureUrl = styleshot.getPictureUrl();
        this.createdAt = styleshot.getCreatedAt();

    }

    public void updateUserInfo(Consumer consumer) {
        UserInfo userInfo = UserInfo.of(consumer);
        this.userInfo = userInfo;
    }



}

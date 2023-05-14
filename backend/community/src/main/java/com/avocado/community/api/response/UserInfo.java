package com.avocado.community.api.response;

import com.avocado.community.db.entity.Consumer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfo {
    String name;
    Integer mbtiId;
    Integer personalColorId;

    public static UserInfo of(Consumer consumer) {
        return new UserInfo(consumer.getName(), consumer.getMbtiId(), consumer.getPersonalColorId());
    }
}

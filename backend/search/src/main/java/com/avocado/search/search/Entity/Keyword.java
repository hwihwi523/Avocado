package com.avocado.search.search.Entity;

import com.avocado.search.search.Dto.response.KeywordRespDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "keywords")
@Setting(settingPath = "static/es-setting.json")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    public KeywordRespDto toDto(){
        KeywordRespDto keywordRespDto =
                KeywordRespDto.builder()
                        .name(this.name)
                        .build();
        return keywordRespDto;
    }
}

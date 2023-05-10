package com.avocado.search.search.Entity;

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
    private int id;

    @Field(type = FieldType.Text)
    private String name;
}

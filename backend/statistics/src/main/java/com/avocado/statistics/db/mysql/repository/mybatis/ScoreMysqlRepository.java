package com.avocado.statistics.db.mysql.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreMysqlRepository {

    public void ageGenderSave();

    public void mbtiSave();

    public void personalColorSave();

    public void deleteAllAgeGender();

    public void deleteAllMbti();

    public void deleteAllPersonalColor();


}

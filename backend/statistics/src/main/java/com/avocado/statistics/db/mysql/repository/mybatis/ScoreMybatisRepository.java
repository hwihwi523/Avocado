package com.avocado.statistics.db.mysql.repository.mybatis;

import com.avocado.statistics.db.mysql.entity.mybatis.AgeGenderScoreMybatis;
import com.avocado.statistics.db.mysql.entity.mybatis.MbtiScoreMybatis;
import com.avocado.statistics.db.mysql.entity.mybatis.PersonalColorScoreMybatis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMybatisRepository {

    void ageGenderSave(AgeGenderScoreMybatis ageGenderScore);

    void mbtiSave(MbtiScoreMybatis mbtiScore);

    void personalColorSave(PersonalColorScoreMybatis personalColorScore);

    void ageGenderBulkSave(List<AgeGenderScoreMybatis> ageGenderScoreList);

    void mbtiBulkSave(List<MbtiScoreMybatis> mbtiScoreList);

    void personalColorBulkSave(List<PersonalColorScoreMybatis> personalColorScoreList);

    void deleteAllAgeGender();

    void deleteAllMbti();

    void deleteAllPersonalColor();


}

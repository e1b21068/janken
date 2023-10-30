package oit.is.z2173.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
    @Select("select * from matchinfo")
    ArrayList<MatchInfo> selectAllMatchInfo();

    @Select("select * from matchinfo where isActive = true")
    ArrayList<MatchInfo> selectActiveMatchInfo();

    @Select("select * from matchinfo where user1 = #{user1} and user2 = #{user2} and isActive = true")
    MatchInfo selectByUsers(int user1, int user2);
    
    @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertMatchInfo(MatchInfo matchInfo);

    @Update("UPDATE matchinfo SET isActive = #{isActive} WHERE id = #{id}")
    void updateMatchInfo(MatchInfo matchInfo);
}

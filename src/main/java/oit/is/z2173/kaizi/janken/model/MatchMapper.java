package oit.is.z2173.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface MatchMapper {

    @Select("SELECT * from matches")
    ArrayList<Match> selectByMatches();
    
    @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand})")
    @Options(useGeneratedKeys = true, keyColumn = "id" , keyProperty = "id")
    void insertMatch(Match match);
}

package com.fastcampus.ch4.dao;


import com.fastcampus.ch4.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BoardDaoImpl implements BoardDao {

    @Autowired  //AutoWired로 session 주입
    SqlSession session;

    String namespace="com.fastcampus.ch4.dao.BoardMapper."; //


    @Override
    public BoardDto select(int bno){
        return  session.selectOne("select", bno);
    }

}

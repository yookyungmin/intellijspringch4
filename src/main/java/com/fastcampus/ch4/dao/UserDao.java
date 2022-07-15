package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.*;
//인터페이스
public interface UserDao {
    User selectUser(String id) throws Exception;
    //public User selectUser2(String id, String pwd) throws Exception; //sql 인덱션 테스트 위해 만듬
    int deleteUser(String id) throws Exception;
    int insertUser(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}
package com.fastcampus.ch4.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageHandlerTest {

    @Test
    public void test(){  //현재 페이지 1일떄
        PageHandler ph = new PageHandler(250, 1); //25개 페이지가 나와야됨 // [RREV] 나오면 안됨
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.beginPage==1);
        assertTrue(ph.endPage==10);
    }

    @Test
    public void test2(){ //현재 페이지 11일떄
        PageHandler ph = new PageHandler(250, 11); //25개 페이지가 나와야됨 
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.beginPage==11);
        assertTrue(ph.endPage==20);
    }
    @Test
    public void test3(){
        PageHandler ph = new PageHandler(255, 25); //26개 페이지가 나와야됨 255/10  [NEXT] 나오면안됨
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.beginPage==21);
        assertTrue(ph.endPage==26);
    }


}
package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class PageHandler {
//    private int page; //현재 페이지
//    private int pageSize; //한페이지의 크기
//
//    private String option;
//    private String keyword;


    private SearchCondition sc;

    private int totalCnt; //총게시물 갯수
    private int naviSize = 10; //페이지 내비게이션의 크기
    private int totalPage; //전체 페이지 갯수
    private int beginPage; //네비게이션의 첫번쨰펭지ㅣ
    private int endPage; //내비게이션의 끝페이지
    private boolean showPrev; //이전 페이지로 이동하는 링크를 보여줄것인지의 여부
    private boolean showNext; //다음 페이지로 이동하는 링크를 보여줄것인지의 여부


    public PageHandler(int totalCnt, SearchCondition sc){
        this.totalCnt = totalCnt;
        this.sc = sc;

        doPaging(totalCnt, sc);
    }

    public void doPaging(int totalCnt, SearchCondition sc){
        this.totalCnt = totalCnt;


         totalPage = (int)Math.ceil(totalCnt/(double)sc.getPageSize()); // 전체 페이지 갯수 =  총 게시물 갯수 / 한 페이지의 크기 // celi 올림
        beginPage = (sc.getPage() - 1)/ naviSize * naviSize +1;
        /* beginpage 시작페이지
        현재 페이지/10*10 +1  */
        endPage = Math.min(beginPage+naviSize-1, totalPage); //totalPage보다 작을수 있기에 min으로 작은것
        showPrev = beginPage!=1; // begin시작페이지가 1이 아니면 이동하는 링크를 보여줄것인지 여부
        showNext = endPage != totalPage; // 내비게이션의 마지막 페이지가 전체 페이지 갯수만 아니면
    }


    public SearchCondition getSc() { //게터
        return sc;
    }

    public void setSc(SearchCondition sc) {
        this.sc = sc; //세터
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }


    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    void print(){   // 페이지 내비게이션 프린트
        System.out.println("page = " + sc.getPage());
        System.out.print(showPrev ? "[PREV]" : ""); //참이면 이전 페이지 넘어갈것인지 링크 보여줌
        for(int i = beginPage; i<=endPage; i++){
            System.out.print(i+ " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "sc=" + sc +
                ", totalCnt=" + totalCnt +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}


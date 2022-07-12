package com.fastcampus.ch4.domain;

public class PageHandler {

    private int totalCnt; //총게시물 갯수
    private int pageSize; //한페이지의 크기
    private int naviSize = 10; //페이지 내비게이션의 크기
    private int totalPage; //전체 페이지 갯수
    private int page; //현재 페이지
    private int beginPage; //네비게이션의 첫번쨰펭지ㅣ
    private int endPage; //내비게이션의 끝페이지
    private boolean showPrev; //이전 페이지로 이동하는 링크를 보여줄것인지의 여부
    private boolean showNext; //다음 페이지로 이동하는 링크를 보여줄것인지의 여부


    public PageHandler(int totalCnt, int page){
        this(totalCnt, page, 10);
    }
    public PageHandler(int totalCnt, int page, int pageSize){
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;
         totalPage = (int)Math.ceil(totalCnt/(double)pageSize); // 전체 페이지 갯수 =  총 게시물 갯수 / 한 페이지의 크기 // celi 올림
        beginPage = (page - 1)/ naviSize * naviSize +1;
        /* beginpage 시작페이지
        현재 페이지/10*10 +1  */
        endPage = Math.min(beginPage+naviSize-1, totalPage); //totalPage보다 작을수 있기에 min으로 작은것
        showPrev = beginPage!=1; // begin시작페이지가 1이 아니면 이동하는 링크를 보여줄것인지 여부
        showNext = endPage != totalPage; // 내비게이션의 마지막 페이지가 전체 페이지 갯수만 아니면 
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
        System.out.println("page = " + page);
        System.out.print(showPrev ? "[PREV]" : ""); //참이면 이전 페이지 넘어갈것인지 링크 보여줌
        for(int i = beginPage; i<=endPage; i++){
            System.out.print(i+ " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}


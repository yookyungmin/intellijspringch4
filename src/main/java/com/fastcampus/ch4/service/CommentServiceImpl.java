package com.fastcampus.ch4.service;

import com.fastcampus.ch4.dao.BoardDao;
import com.fastcampus.ch4.dao.CommentDao;
import com.fastcampus.ch4.domain.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
   //댓글 서비스에서는 댓글이 추가되거나 삭제되면 BoardDao에도 영향이 가기때문에 두가지 다 @Autowired로 주입 
    //받아야함
    
    //BoardDadImpl @Repository 되어있으면 @AutoWired주입이이안됨
//    @Autowired
    BoardDao boardDao;
//    @Autowired
    CommentDao commentDao; //인스턴스 변수에 필드 주입이기에 아래 생성자 주입을 써야좋다

    //필드주입 대신 생성자 주입을 하면 컴파일시 매개변수 에러 판단 가능

//    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)  //checked 예외는 롤백 안됨
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        int rowCnt = boardDao.updateCommentCnt(bno, -1);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
//        throw new Exception("test");
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);
        return rowCnt;
        //updateCommentCnt, delete  작업 두가지를 하기위해 @Transactional
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCnt(commentDto.getBno(), 1);
//                throw new Exception("test");
        return commentDao.insert(commentDto);
    }

    @Override
    public List<CommentDto> getList(Integer bno) throws Exception {
//        throw new Exception("test");
        return commentDao.selectAll(bno);
    }

    @Override
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}
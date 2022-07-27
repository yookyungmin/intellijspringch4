package com.fastcampus.ch4.controller;


import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController  //메서드별 앞에 @ResoponseBody 지우고 입력 //위 두개 합친것
public class CommentController {
    @Autowired
    CommentService service;
   /*
    {
        "pcno":0,
            "comment": "hihihi",
            "commenter":"asdf"
    }
    */
    //댓글을 수정하는 메서드

    @PatchMapping("/comments/{cno}")// /ch4/comments/70 patch
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session){
        //String commenter = (String)session.getAttribute("id");
        String commenter = "asdf"; //주석 처리하고 위에문장 풀어야
        dto.setCommenter(commenter); //로그인을 안하고 있기떄문에 하는것 원래는
        dto.setCno(cno);
        System.out.println("dto ="+dto);
        try {
            if(service.modify(dto)!=1)
                throw new Exception("write failed");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    /*{
"pcno":0,
"comment": "hi"
}
    * */
    //댓글을 등록하는 메소드

    @PostMapping("/comments")// /ch4/comments?bno=1085 POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session){
        //String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter); //로그인을 안하고 있기떄문에 수동으로 셋팅중, 컨트롤러에서
        dto.setBno(bno);
        System.out.println("dto= " +dto);
        try {
           if(service.write(dto)!=1)
           throw new Exception("write failed");

           return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    //지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}") // Delete/comments/1?bno=1085 <-- 삭제할 댓글 번호

    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
        //String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                    throw new Exception("Delete Failed");
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }
    //지정된 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments") //comments?bno=1800 get
      public ResponseEntity<List<CommentDto>> list(Integer bno){ //CommentDto를 반환하는 list메소드
        List<CommentDto> list = null;
        try {
             list = service.getList(bno);
        return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); //성공, 200번
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); //400
        }
    }
}

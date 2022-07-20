package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.desktop.UserSessionEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){ //글 수정
        String writer = (String)session.getAttribute("id"); //글쓴사람 id
        boardDto.setWriter(writer); //글쓴사람id

        try {
            int rowCnt = boardService.modify(boardDto); //insert
            if(rowCnt !=1) //rowCnt 1이 아니면 예욀르 던짐
                throw new Exception("Moidfy failed");

            rattr.addFlashAttribute("msg", "MOD_OK");//ratter사용으로 한번 메시지

            return "redirect:/board/list"; //게시물목록으로 돌아감
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("boadrdDto", boardDto);
            m.addAttribute(boardDto); //위 문장과 동일
            m.addAttribute("msg", "MOD_ERR"); //왜 진행이 안됐는지 메시지 아림
            return "board"; //에러나면 글쓰기 화면 보여주기 , 글썼떤 내용 살리기 //rowCnt1 1이 아니면
        }
    }
    @PostMapping("/write")
   public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){ //글쓰기 등록
        String writer = (String)session.getAttribute("id"); //글쓴사람 id
        boardDto.setWriter(writer); //글쓴사람id

        try {
            int rowCnt = boardService.write(boardDto); //insert
            if(rowCnt !=1) //rowCnt 1이 아니면 예욀르 던짐
                throw new Exception("Write failed");

                rattr.addFlashAttribute("msg", "WRT_OK");//ratter사용으로 한번 메시지

            return "redirect:/board/list"; //게시물목록으로 돌아감
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("boadrdDto", boardDto);
            m.addAttribute(boardDto); //위 문장과 동일
            m.addAttribute("msg", "WRT_ERR"); //왜 진행이 안됐는지 메시지 아림
            return "board"; //에러나면 글쓰기 화면 보여주기 , 글썼떤 내용 살리기 //rowCnt1 1이 아니면
        }


    }
    @GetMapping("/write") //글쓰기 화면으로  이동
    public String write(Model m){
            m.addAttribute("mode", "new");
            return "board"; //읽기아 쓰기에 사용, 쓰기에 사용할떈 mode=new
    }
    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){ //게시글 삭제 기능
        String writer = (String)session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer); //삭제 되면 1
            if(rowCnt!=1)
                throw new Exception("board remove error"); //1이 아니면 예외 발생으로 43번으로 이동
            
                rattr.addFlashAttribute("msg","DEL_OK"); //삭제되었다는 알림 세션을 이용하여 한번 메시지 나오고 안나옴

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR"); //삭제시 에러
        }
            return "redirect:/board/list";
    }
    @GetMapping("/read")  //게시물 읽기기능
    public String read(Integer bno, Integer page, Integer pageSize, Model m){
        try {
            BoardDto boardDto = boardService.read(bno);
            //m.addAttribute("boardDto", boardDto); //아래문장과동일
            m.addAttribute(boardDto); //이름을 생랴갛면 타입 BoardDto의 첫글자를소문자로한걸 저장
            m.addAttribute("page", page); //뷰로 넘김
            m.addAttribute("pageSize", pageSize);

        } catch (Exception e) {
           e.printStackTrace();
        }
        return "board"; //read 메서드 호출되면 board보여줌
    }
    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
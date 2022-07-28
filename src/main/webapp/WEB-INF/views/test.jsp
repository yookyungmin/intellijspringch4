<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>
comment: <input type = "text" name ="comment"><br>
<button id="sendBtn" type="button">SEND</button>
<button id="modBtn" type="button">수정</button>

<div id="commentList"></div>
<div id ="replyForm" style="display: none">
  <input type ="text" name="replyComment">
  <button id="wrtRepBtn" type = "button">등록</button>
</div>
<script>
  let bno= 1085;
  let showList = function (bno){
    $.ajax({
      type:'GET',       // 댓글 록록 가져오는 요청메소드는 GET
      url: '/ch4/comments?bno='+bno,  // 요청 URI
      success : function(result){
        $("#commentList").html(toHtml(result));    // 서버로부터 응답이 도착하면 호출될 함수
      },
      error  : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()
  }
  $(document).ready(function() {
    showList(bno);

    $("#modBtn").click(function () {//수정적용기능
      let cno =$(this).attr("data-cno"); //cno 가져오기
      let comment = $("input[name=comment]").val(); //입력한 내용을가져와서 comment 변수에 담고, js객체로 만들어서 보낸다

      if (comment.trim() == '') { //댓글이 공백이면 알림뜸
        alert("댓글을 입력해주세요");
        $("input[name=comment]").focus()
        return;
      }
      $.ajax({
        type: 'PATCH',       // 요청 메서드
        url: '/ch4/comments/'+cno,  // 요청 URI // /ch4/comments/70 patch
        headers: {"content-type": "application/json"}, // 요청 헤더
        data: JSON.stringify({cno: cno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function (result) { //결과 알려주기
          alert(result);
          showList(bno);//화면에 댓글 목록 보여주기
        },
        error: function () {
          alert("error")
        } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()

    });

    $("#sendBtn").click(function () {//댓글 쓰기
      let comment = $("input[name=comment]").val(); //입력한 내용을가져와서 comment 변수에 담고, js객체로 만들어서 보낸다

      if (comment.trim() == '') { //댓글이 공백이면 알림뜸
        alert("댓글을 입력해주세요");
        $("input[name=comment]").focus()
        return;
      }
      $.ajax({
        type: 'POST',       // 요청 메서드
        url: '/ch4/comments?bno=' + bno,  // 요청 URI // /ch4/comments?bno=1085 POST
        headers: {"content-type": "application/json"}, // 요청 헤더
        data: JSON.stringify({bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function (result) { //결과 알려주기
          alert(result);
          showList(bno);//화면에 댓글 목록 보여주기
        },
        error: function () {
          alert("error")
        } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()

    });
    $("#wrtRepBtn").click(function () {//답글 등록
      let comment = $("input[name=replyComment]").val(); //입력한 내용을가져와서 comment 변수에 담고, js객체로 만들어서 보낸다
      let pcno = $("#replyForm").parent().attr("data-pcno"); //pcno 가져오기 //답글 밑에 답글 달리게

      if (comment.trim() == '') { //댓글이 공백이면 알림뜸
        alert("댓글을 입력해주세요");
        $("input[name=replyComment]").focus()
        return;
      }
      $.ajax({
        type: 'POST',       // 요청 메서드
        url: '/ch4/comments?bno=' + bno,  // 요청 URI // /ch4/comments?bno=1085 POST
        headers: {"content-type": "application/json"}, // 요청 헤더
        data: JSON.stringify({pcno: pcno, bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function (result) { //결과 알려주기
          alert(result);
          showList(bno);//화면에 댓글 목록 보여주기
        },
        error: function () {
          alert("error")
        } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()


          $("#replyForm").css("display","none"); //답글 등록 요청 후 replyForm을 안보이게
          $("input[name=replyComment]").val('') //값 비우기
          $("#replyForm").appendTo("body"); //원래 위치로 되돌려 놓기

    });

    $("#sendBtn").click(function () {//댓글 쓰기
      let comment = $("input[name=comment]").val(); //입력한 내용을가져와서 comment 변수에 담고, js객체로 만들어서 보낸다

      if (comment.trim() == '') { //댓글이 공백이면 알림뜸
        alert("댓글을 입력해주세요");
        $("input[name=comment]").focus()
        return;
      }
      $.ajax({
        type: 'POST',       // 요청 메서드
        url: '/ch4/comments?bno=' + bno,  // 요청 URI // /ch4/comments?bno=1085 POST
        headers: {"content-type": "application/json"}, // 요청 헤더
        data: JSON.stringify({bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function (result) { //결과 알려주기
          alert(result);
          showList(bno);//화면에 댓글 목록 보여주기
        },
        error: function () {
          alert("error")
        } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()

    });

    $("#commentList").on("click", ".modBtn", function () { //댓글삭제 // .modBtn = 각 댓글마다 붙어있는는 수정버튼
      let cno = $(this).parent().attr("data-cno"); //cno 가져오기
      let comment = $("span.comment", $(this).parent()).text();//클릭돈 수정버튼의 부모의 span.comment
      //1.comment 의 내용을 input 에 뿌려주기
      $("input[name=comment]").val(comment);
      //2.cno 전달하기
      $("#modBtn").attr("data-cno", cno); //#modBtn  멘위에 붙어있는 수정버튼
    });

    $("#commentList").on("click", ".replyBtn", function () { //대댓글 기능

      //1 replyForm을 옮기고
      $("#replyForm").appendTo($(this).parent()); //this= replyBtn의 부모 li태그 뒤에 붙이기
      //2.답글 입력할 폼을 보여주고,
      $("#replyForm").css("display", "block");
    });
      // $(".delBtn").click(function (){  <!--클래스 .delBtn -->
      $("#commentList").on("click", ".delBtn", function () { //댓글삭제
        let cno = $(this).parent().attr("data-cno"); //cno 가져오기
        let bno = $(this).parent().attr("data-bno"); //bno가져오기
        $.ajax({
          type: 'DELETE',       // 댓글 록록 가져오는 요청메소드는 GET
          url: '/ch4/comments/' + cno + '?bno=' + bno,  // 요청 URI
          success: function (result) {
            alert(result)
            showList(bno);
          },
          error: function () {
            alert("error")
          } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
      });
    });
    let toHtml = function (comments) {
      let tmp = "<ul>";

      comments.forEach(function (comment) {
        tmp += '<li data-cno=' + comment.cno
        tmp += ' data-pcno=' + comment.pcno
        tmp += ' data-bno=' + comment.bno + '>'
        if(comment.cno!=comment.pcno)
          tmp+='ㄴ'
        tmp += ' commenter=<span class="commenter">' + comment.commenter + '</span>'
        tmp += ' comment=<span class="comment">' + comment.comment + '</span>'
        tmp += ' up_date=' + comment.up_date
        tmp += '<button class="delBtn">삭제</button>'
        tmp += '<button class="modBtn">수정</button>'
        tmp += '<button class="replyBtn">답글</button>'
        tmp += '</li>'
      })
      return tmp + "</ul>";
    }
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script> <!--제이쿼리 쓰기위해서, 목록버튼-->
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='/login/login'/>">login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<script>
    let msg="${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패했씁니다. 다시 시도해주세요");
</script>
<div style="text-align:center">
    <h2>게시물 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form action="" id = "form">
        <input type="hidden" name="bno" value="${boardDto.bno}" >
        <input type = "text" name="title" value="${boardDto.title}" ${mode=="new"? '':'readonly="readonly"'}>
        <textarea name = "content" id="" cols="30" rows="10" ${mode=="new"? '':'readonly="readonly"'}></textarea> <!--글쓰기인지 읽기인지 구분-->
        <button type="button" id="writeBtn" class="btn">글쓰기</button>
        <button type="button" id="modifyBtn" class="btn">수정</button>
        <button type="button" id="removeBtn" class="btn">삭제</button>
        <button type="button" id="listBtn" class="btn">목록</button>
    </form>

    </div>
</div>
<script>
    $(document).ready(function(){//제이쿼리, 목록 버튼 누르면 페이지 바뀜main() 메인 메서드와 같다
        $('#listBtn').on("click", function (){
            location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
        });  <!-- location의 url 검색창과 같아서 브라우저창에 직접 입력하는거와 같다 get방식-->
        $('#removeBtn').on("click", function (){ <!-- 삭제기능-->
            if(!confirm("정말로 삭제하시겠습니까")) return; //삭제전 여부
           let form =  $('#form');
           form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");
           form.attr("method", "post"); //메소드는 포스트방식
           form.submit();
        });
        $('#writeBtn').on("click", function (){ //글 등록
            let form =  $('#form');
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post"); //메소드는 포스트방식
            form.submit();
        });
        $('#modifyBtn').on("click", function (){ //글 수정
            //1.읽기 상태이면 수정 상태로 변경
            let form = $('#form');
            let isReadOnly = $("input[name=title]").attr('readonly');

            if(isReadOnly=='readonly'){
                $("input[name=title]").attr('readonly', false); //title , 제목
                $("textarea").attr('readonly', false); //content
                $("#modifyBtn").html("등록");
                $("h2").html("게시물 수정");
                return;
            }
            //2. 수정 상태이면 수정된 내용을 서버로 전송

            form.attr("action", "<c:url value='/board/modify'/>");
            form.attr("method", "post"); //메소드는 포스트방식
            form.submit();
        });
    });
</script>
</body>
</html>
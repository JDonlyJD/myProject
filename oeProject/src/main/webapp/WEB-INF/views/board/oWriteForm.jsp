<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1문의게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#kind').val().trim()==''){
				alert('질문의 분류를 선택하세요!');
				return false;
			}
			if($('#title').val().trim()==''){
	            alert('제목을 입력하세요!');
	            $('#title').val('').focus();
	            return false;
	        }
			if($('#content').val().trim()==''){
	            alert('내용을 입력하세요!');
	            $('#content').val('').focus();
	            return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>1:1문의게시판</h2>
	<form id="write_form" action="askWrite.do" method="post">
		<ul>
			<li>
				<label for="kind">질문분류</label>
            <select name="kind">
               <option value="0">신고합니다</option>
               <option value="1">상품문의</option>
               <option value="2">광고문의</option>
            </select>
            <p>
         	</li>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" maxlength="50">
			</li>
			<p>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" name="content" id="content"></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="작성">
		</div>
	</form>
</div>
</body>
</html>
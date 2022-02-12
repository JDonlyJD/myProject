<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		 $('#write_form').submit(function(){
			 /*if($('input[type=radio]:checked').length < 1){
				alert('카테고리를 선택하세요.');
				return false;
			} */ 
			 if($('#cate').val()==''){
					alert('카테고리를 선택하세요.');
					$('#cate').focus();
					return false;
				}
			if($('#title').val().trim()==''){
				alert('판매글 제목을 입력하세요.');
				$('#title').val('').focus();
				return false;
			}
			if($('#price').val()==''){
				alert('가격을 입력하세요.');
				$('#price').focus();
				return false;
			}
			if($('#filename').val().trim()==''){
				alert('판매상품 사진을 등록하세요.');
				$('#filename').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요.');
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
	<h2>판매 상품 등록</h2>
	<form action="itemWrite.do" method="post" enctype="multipart/form-data" id="write_form">
	<!-- <input type="hidden" name="mem_num" value="${member.mem_num}">  -->
	<ul>
		<li>
			<label for="cate">카테고리</label>
			<input type="number" name="cate" id="cate" min="1" max="9999999">				
		</li>
		<li>
			<label for="title">판매글 제목</label>
			<input type="text" name="title" id="title">				
		</li>
		<li>
			<label for="price">가격</label>
			<input type="number" name="price" id="price" min="1" max="9999999">				
		</li>
		<li>
			<label for="filename">판매상품 사진</label>
			<input type="file" name="filename" id="filename" 
									accept="image/gif,image/png,image/jpeg">				
		</li>
		<li>
			<label for="content">내용</label>
			<textarea name="content" id="content" rows="5" cols="30"></textarea>			
		</li>
	</ul>
	<div class="align-center">
		<input type="submit" value="등록">
		<!-- <input type="button" value="목록으로" onclick="location.href='salelist.do'"> -->
	</div>
	</form>
</div>	
</body>
</html>
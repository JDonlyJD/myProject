<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매상품 글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css"> <%--css스타일 경로--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board-reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>판매상품 글상세 </h2>
	<ul>
		<li>판매자 : ${item.mem_id}</li>
		<li>판매상황 : ${item.state}</li>
		<li>조회수 : ${item.hit}</li>
		<li>카테고리 : ${item.cate_num}</li>
		<li>가격 : ${item.price}</li>
	</ul>
	<hr size="1" noshade width="100%">
	<c:if test="${!empty item.filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${item.filename}" class="detail-img">
		</div>
	</c:if>
	<p>
		${item.content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-right">
		<c:if test="${!empty board.modify_date}">
			최근 수정일 : ${item.modify_date}		<%--수정일이 있으면 최근수정일 보이도록 --%>
		</c:if>		
		작성일 : ${item.reg_date}
		
		<%--로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>	
		<c:if test="${user_num == item.mem_num }">	
			<input type="button" value="수정" onclick="location.href='itemUpdateForm.do?item_num=${item.item_num}'">
			<input type="button" value="삭제" id="delete_btn">		<!-- 수정,삭제페이지 안만들었음 -->
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('itemDelete.do?item_num=${item.item_num}'); 
							//히스토리를 지우고(전페이지로 back불가) + delete로 이동하고 싶으면 location.replace
							//히스토리를 남기면서(back가능) 이동하고 싶으면 location.href를 쓰면됨
					}
				};
			</script>	
		</c:if>	
		<input type="button" value="목록" onclick="location.href='salelist.do'">	<!-- 목록? -->
	</div>
	</div>
	</body>
</html>
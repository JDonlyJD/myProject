<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- header 시작 -->
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css"> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header_footer.css">

</head>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<div class="header">
	<header id="main_header" >
	 
<%-- ===첫번째 메뉴=== --%>
	<%-- **로고 --%>
		<div class="logo-img">
			<a href="${pageContext.request.contextPath}/main/main.do">
				<img src="${pageContext.request.contextPath}/images/oi.png" width="65" height="60" class="logo-img">
			</a>
		</div>
		<div class="logo-txt">
			<a href="${pageContext.request.contextPath}/main/main.do">
			<h2><b id="logo_text">52MARKET</b></h2></a>
		</div>
		 
	<%-- **검색창 --%>
 		<form class="navbar-form navbar-right">
            <div class="form-group">
              <input type="text" class="form-control input-lg" placeholder="오늘은 이거 ? ! " />
            </div>
            <button type="submit" id="search_btn" class="btn btn-success btn-lg" >검색</button>
          </form>
		
		<!-- <a id="menu_search" href="#">
			<input type="search" name="search" size="30" value="검색! 오이해보세요 ">
		</a> -->
	
	<%-- **관리자)회원관리 --%>	
		<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
			<a href="${pageContext.request.contextPath}/member/adminMemberList.do">회원관리</a>
		</c:if>
		
	<%-- **MY HOME/MAMAGER HOME--%>	
		<c:if test="${!empty user_num}"><%--로그인 했을 때--%>
			<c:if test="${!empty user_num && user_auth < 2}"> <%--회원일 경우--%>
				<a href="${pageContext.request.contextPath}/member/myHome.do">MY HOME</a>
			</c:if>
			<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
				<a href="${pageContext.request.contextPath}/member/myHome.do">MANAGER HOME</a>
			</c:if>
		</c:if>
		
	<%-- **프로필 사진/user아이디 --%>		 
		<c:if test="${!empty user_num && !empty user_photo }"> <%--회원, 사진O--%>
			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}"> <%--회원, 사진X--%>
			<img src="${pageContext.request.contextPath}/images/oi.png" width="30" height="25" class="my-photo">
		</c:if>
		<c:if test="${!empty user_num}">
			<c:if test="${!empty user_num && user_auth < 2}"> <%--회원일 경우--%>
				<a><span id="menu_logout">[&nbsp;${user_id}&nbsp;]회원 님</span>&nbsp;</a>
			</c:if>
			<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
				<a><span id="menu_logout">[&nbsp;${user_id}&nbsp;]관리자</span>&nbsp;</a>
			</c:if>
		</c:if>
	
	<%-- **로그인/로그아웃 --%>			
		<c:if test="${empty user_num}"><%--로그인 안했을 때--%>
			<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
		</c:if>
		<c:if test="${!empty user_num }"><%--로그인 했을 때--%>
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</c:if>
	</header>
	
<%-- ===두번째 메뉴=== --%>
	<div class="second-menu">
	<nav class="navbar navbar-nav centered " id="second_menu" >
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          	<ul class="nav navbar-nav">
	<%-- **카테고리 --%>			
		      	<li class="dropdown ">
		        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">카테고리 </a>
		        	<ul class="dropdown-menu">
				        <li><a class="dropdown" href="#">Cloth</a></li>
				        <li><a class="dropdown" href="#">Beauty</a></li>
			        	<li><a class="dropdown" href="#">Tech</a></li>
				        <li><a class="dropdown" href="#">Food</a></li>
				        
				        <li><a class="dropdown" href="#">Ect</a></li>
			        </ul>
		      	</li>
		      	
				
		      
	<%-- **문의게시판 --%>			
		       	<c:if test="${!empty user_num }"><%--로그인 했을 때--%>
		        	<li ><a href="${pageContext.request.contextPath}/board/list.do">문의 게시판</a></li>
		        </c:if>	
		        
	<%-- **채팅목록 --%>			
		        <%-- active로 기본적으로 보이게한다 --%>
		        <c:if test="${!empty user_num }"><%--로그인 했을 때 --%>
		        	<li><a href="#">채팅목록</a></li>
		        </c:if>
		        
	<%-- **상품등록 --%>			
		        <c:if test="${!empty user_num }"><%--로그인 했을 때--%>
		            <li><a href="${pageContext.request.contextPath}/item/ItemWriteForm.do">상품등록</a></li>
	            </c:if>
          </ul>
        </div>
        
    </nav>
    </div>
     
</div>	
</body>
</html>
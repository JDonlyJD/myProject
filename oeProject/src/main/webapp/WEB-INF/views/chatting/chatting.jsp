<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
</head>
<body>

<header>
	판매상품  <br>
	" ${item.title } "의 <br>
	채팅방입니다.
</header>
<!-- 채팅 시작 -->
   <div id="chat_div">
      <div id="chatbox_div">
      <span class="re-title">.</span>
      
      <form id="re_form">
         <input type="hidden" name="item_num" value="${item.item_num }" id="item_num">
         <input type="hidden" name="to_num" value="${item.mem_num}" id="to_num">
         <textarea rows="3" cols="50" name="content" id="content" class="rep-content" 
         
         <c:if test="${empty user_num}">disabled="disabled"</c:if>
         ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
      <c:if test="${!empty user_num}">
      
      <div id="re_first">
         <input type="button" value="뒤로가기" onclick="location.href='${pageContext.request.contextPath}/item/itemDetail.do?item_num=${item.item_num}'">
      </div>
      
      <div id="re_second" class="align-right">
         <input type="submit" value="전송">
      </div>
      
      </c:if>
      </form>
      </div>
   </div>
   <!-- 채팅 목록 출력 시작 -->
   <div id="chat_output"><div id="output"></div></div>
   <div class="paging-button" style="display:none;">
      <input type="button" value="다음글 보기">
   </div>
   <div id="loading" style="display:none;">
      <img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
   </div>
   <!-- 채팅 목록 출력 끝 -->
   <!-- 채팅 끝 -->
 
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>구매내역</h2>
	<form action="buyList.do" method="post" id="buylist_form">
		<table border="1">
		<tr>
			<th>구매번호</th>
			<th>제목</th>
			<th>판매자</th>
			<th>구매가격</th>
			<th>구매날짜</th>
		</tr>
		<c:forEach var="order" items="${orderList}">
		<tr>
			<td>${order.order_num}</td>
			<td><a href="saleList.do?order_num=${order.mem_num}">${order.title}</a></td>
			<td>${order.buyer_id}</td>
			<td>${order.price}</td>
			<td>${order.reg_date}</td>
		</tr>
		</c:forEach>
		</table>
	</form>
	<br>
	<div class="align-center">
      <input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/member/myHome.do'">
      <input type="button" value="홈으로" 
       onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
   </div>
   <div class="align-center">
      ${pagingHtml}
   </div>
</div>
</body>
</html>
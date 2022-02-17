<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js">/* 컨텍스트경로부터 명시해야 에러안남 */</script>
<!-- <script type="text/javascript">
	$(function () {
		$('#search_form').submit(function () {
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script> -->
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
		<h2>판매 내역</h2>
		<form action="saleList.do" method="get" id="search_form">
			<div class="align-center">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1">제목+내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword }"><br><!-- value = 검색을 했다면 검색한것이 남아있게함 선택사항임 -->
					<input type="submit" value="검색">
				</li>
			</ul>
			</div>
		</form>
		<%-- <div class="list-space align-right">
			<input type="button" value="글쓰기" 
				onclick="location.href='writeForm.do'" <c:if test="${empty user_num}">disabled="disabled"</c:if>>  <!-- 같은경로이기 떄문에 파일경로만 작성했음  -->
			<input type="button" value="홈으로" 
				onclick="location.herf='${pageContext.request.contextPath}/main/main.do'">
		</div> --%>
		<br>
		<c:if test="${count == 0 }">
			<div class="result-display">
				표시할 게시물이 없습니다.
			</div>
		</c:if>
		<c:if test="${count > 0 }">
			<table>
				<tr>
					<th>상품번호</th>
					<th>제목</th>
					<th>판매상태</th>
					<th>상품가격</th>
					<th>등록일</th>
				</tr>
				<c:forEach var="item" items="${list }">
					<tr>
						<td>${item.item_num }</td>
						<td><a href="itemDetail.do?item_num=${item.item_num}" style="color: black;">${item.title }</a></td>
						<td>
							<c:if test="${item.state == 0 }">판매중</c:if>
							<c:if test="${item.state == 1 }">예약중</c:if>
							<c:if test="${item.state == 2 }">판매완료</c:if>
						</td>						
						<td><fmt:formatNumber value="${item.price}" pattern="#,###"/></td>
						<td>${item.reg_date }</td>
						<!-- 판매상태(0판매중/1예약중/2판매완료) (default 0) -->
						<%-- <td>
							<c:if test="${item.state == 1 }">미표시</c:if>
							<c:if test="${item.state == 2 }">표시</c:if>
						</td> --%>						
					</tr>
				</c:forEach>
			</table>
			<div class="align-center">
				<br><a href="${pageContext.request.contextPath}/main/main.do"><img alt="홈으로" src="${pageContext.request.contextPath}/images/home.jpg" width="30px" height="30px"></a>
				<br><br><br>${pagingHtml}
			</div>
		</c:if>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
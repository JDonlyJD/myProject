<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="result-display">
		<div class="align-center">
			52marcket 회원가입이 완료되었습니다.
			<br>
			<br>
			<%-- <input type="button" value="홈으로" 
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'"> --%>
			<a href="${pageContext.request.contextPath}/main/main.do"><img alt="홈으로" src="${pageContext.request.contextPath}/images/home.jpg" width="30px" height="30px"></a>
		</div>
	</div>
</div>
</body>
</html>
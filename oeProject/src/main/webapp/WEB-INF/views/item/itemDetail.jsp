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
<script type="text/javascript">
	$(function(){
   	 let photo_path = $('.my-file').attr('src');//처음 화면에 보여지는 이미지 읽기(기본값 셋팅)
    	let my_photo;
       
    	//***이벤트3 )이미지 선택 및 이미지 미리보기
   		 $('#filename').change(function(){
      	 my_photo = this.files[0];
      	 if(!my_photo){
      	    $('.my-file').attr('src',photo_path);//처음 원본 이미지로 설정
        	  return;
         }
     	  
       //용량체크
        if(my_photo.size > 1024*1024){
           alert('1MB까지만 업로드 가능!');
           $(this).val(''); //선택하지 못하게 막기
           return;
        }
       
        var reader = new FileReader(); //파일객체
        reader.readAsDataURL(my_photo);
       
        reader.onload=function(){
           $('.my-file').attr('src',reader.result);
       	};
   	 });//end of change
 	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>판매상품 글상세 </h2>
	<ul>
		<li>제목 : ${item.title}</li>
		<li>판매자 : ${item.mem_id}</li>
		<li>
			<c:if test="${item.state == 0 }">판매상황 : 판매중</c:if>
			<c:if test="${item.state == 1 }">판매상황 : 예약중</c:if>
			<c:if test="${item.state == 2 }">판매상황 : 판매완료</c:if>
		</li>
		<li>조회수 : ${item.hit}</li>
		<li>카테고리 : ${item.cate_num}</li>
		<li>가격 : ${item.price}</li>
	</ul>
	<hr size="1" noshade width="100%">
	<c:if test="${!empty item.filename}">
		<div class="align-center">
		<ul>
			<%-- 사진 미리보기 --%>
      <li>
         <c:if test="${empty item.filename}">
         <img src="${pageContext.request.contextPath}/images/face.png" 
               width="200" height="200" class="my-file">
         </c:if>
         <c:if test="${!empty item.filename}">
         <img src="${pageContext.request.contextPath}/upload/${item.filename}"
                           width="200" height="200" class="my-file">
         </c:if>
      </li>
		</ul>
			<%-- <img src="${pageContext.request.contextPath}/upload/${item.filename}" class="detail-img"> --%>
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
			<input type="button" value="삭제" id="delete_btn" onclick="location.href='itemDelete.do?item_num=${item.item_num}'">		
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
		<input type="button" value="목록" onclick="location.href='saleList.do'">	
	</div>
	</div>
	</body>
</html>
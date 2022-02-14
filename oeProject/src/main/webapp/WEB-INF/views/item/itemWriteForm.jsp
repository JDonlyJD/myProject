<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

         if($('#cate_num').val()==''){
            alert('카테고리를 지정하세요!');      //맞는지 확인하기
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
         if($('#filename').val()==''){
            alert('판매상품 사진을 등록하세요.');
            $('#filename').focus();
            return false;
         }
         if($('#content').val().trim()==''){
            alert('내용을 입력하세요.');
            $('#content').val('').focus();
            return false;
         }
      });
   });
   
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
   <h2>판매 상품 등록</h2>
   <form action="itemWrite.do" method="post" enctype="multipart/form-data" id="write_form">
   <!-- <input type="hidden" name="mem_num" value="${member.mem_num}">  -->
   <ul>
      <li>
         <label>카테고리 여부</label>
         <input type="number" name="cate_num" id="cate_num">
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
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
			 /*if($('input[type=radio]:checked').length < 1){
				alert('카테고리를 선택하세요.');
				return false;
			} */ 
			
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
		let photo_path = $('.my-photo').attr('src');//처음 화면에 보여지는 이미지 읽기(기본값 셋팅)
		let my_photo;
		
		$('#photo_btn').click(function(){
			$('#photo_choice').show(); //안보여지는 것을 보여지게 변경
			$(this).hide(); //수정 버튼 감추기
		});
		//이미지 미리보기 취소
		$('#photo_reset').click(function(){
			//이미지 미리보기 전 이미지로 되돌리기(보관 후 사용)
			$('.my-photo').attr('src',photo_path); //취소하면 원래 이미지로 변경
			$('#photo').val(''); //지워주기(초기화작업들)
			$('#photo_choice').hide();
			$('#photo_btn').show(); //이미지 미리보기를 취소하므로 다시 보여지게, 수정버튼 노출
		});
		
		//이미지 선택 및 이미지 미리보기
		$('#photo').change(function(){
			my_photo = this.files[0];
			if(!my_photo){
				$('.my-photo').attr('src',photo_path);//처음 원본 이미지로 설정
				return;
			}
			
			//용량체크
			if(my_photo.size > 1024*1024){
				alert('1MB까지만 업로드 가능!');
				photo.value = ''; //선택하지 못하게 막기
				return;
			}
			
			var reader = new FileReader(); //파일객체
			reader.readAsDataURL(my_photo);
			
			reader.onload=function(){
				$('.my-photo').attr('src',reader.result);
			};
		});//end of change
		
		//이미지 전송
		$('#photo_submit').click(function(){
			if($('#photo').val() == ''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			//파일전송
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			//ajax통신하기
			$.ajax({
				//옵션값 저장(키와 값의 쌍으로 명시)
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false, //데이터 객체를 문자열로 바꿀지 지정 true이면 일반 문자
				processData:false, //해당 타입을 true로하면 일반text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('사진이 수정되었습니다.');
						photo_path = $('.my-photo').attr('src'); //이미지 수정->취소할때 이전 이미지로 돌아가는 작업
						//초기화 작업
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					}else{
						alert('파일전송 오류 발생!');
					}
				},
				error:function(){
					alert('네트워크 오류발생!');
				}
			});
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
		<!-- 카테고리 추가해야함 cate --> 
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
			<c:if test="${empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" 
					width="200" height="200" class="my-photo">
			</c:if>
			<c:if test="${!empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}"
									width="200" height="200" class="my-photo">
			</c:if>
		</li>
		<li>
			<div class="align-center">
				<input type="button" value="수정" id="photo_btn">
			</div>
			<div id="photo_choice" style="display: none;">
				<input type="file"
					id="photo" accept="image/gif,image/png,image/jpeg"><br>
					<input type="button" value="전송" id="photo_submit">
					<input type="button" value="취소" id="photo_reset">
			</div>
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
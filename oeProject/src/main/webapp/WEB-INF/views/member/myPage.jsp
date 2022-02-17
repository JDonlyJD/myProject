<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(function() {
		let photo_path = $('.my-photo').attr('src');//처음 화면에 보여지는 이미지 읽기
		let my_photo;

		$('#photo_btn').click(function() {
			$('#photo_choice').show();
			$(this).hide();//수정 버튼 감추기
		});
		//이미지 미리보기 취소
		$('#photo_reset').click(function() {
			//이미지 미리보기 전 이미지로 되돌리기
			$('.my-photo').attr('src', photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show(); //수정 버튼 노출
		});

		//이미지 선택 및 이미지 미리보기
		$('#photo').change(function() {
			my_photo = this.files[0];
			if (!my_photo) {
				$('.my-photo').attr('src', photo_path);
				return;
			}

			if (my_photo.size > 1024 * 1024) {
				alert('1MB까지만 업로드 가능!');
				photo.value = '';
				return;
			}

			var reader = new FileReader();
			reader.readAsDataURL(my_photo);

			reader.onload = function() {
				$('.my-photo').attr('src', reader.result);
			};
		});//end of change

		//이미지 전송
		$('#photo_submit').click(function() {
			if ($('#photo').val() == '') {
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}

			//파일 전송
			let form_data = new FormData();
			form_data.append('photo', my_photo);
			$.ajax({
				url : 'updateMyPhoto.do',
				type : 'post',
				data : form_data,
				dataType : 'json',
				contentType : false, //데이터 객체를 문자열로 바꿀지 지정 true이면 일반문자
				processData : false, //해당 타입을 true 로 하면 일반 text로 구분
				enctype : 'multipart/form-data',
				success : function(param) {
					if (param.result == 'logout') {
						alert('로그인 후 사용하세요!');
					} else if (param.result == 'success') {
						alert('프로필 사진이 수정되었습니다.');
						photo_path = $('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					} else {
						alert('파일 전송 오류 발생');
					}
				},    
				error : function() {
					alert('네트워크 오류 발생');
				}
			});
		});
	});
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>회원정보</h2>
	<div class="mypage-div">
 		<h3>프로필 사진</h3>
		<ul>
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
					<div id="photo_choice" style="display:none;">
					<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
					<input type="button" value="전송" id="photo_submit">
					<input type="button" value="취소" id="photo_reset">
				</div>
			</li>
		</ul>
		<h3>회원탈퇴</h3>
		<ul>
			<li>
				<input type="button" value="회원탈퇴" onclick="location.href='deleteUserForm.do'">
			</li>
		</ul>
	</div>
	<div class="mypage-div">
		<h3>연락처</h3>
		<ul>
			<li>이름 : ${member.mem_nick}</li>
			<li>전화번호 : ${member.mem_phone}</li>
			<li>이메일 : ${member.mem_email}</li>
			<li>우편번호 : ${member.mem_zipcode}</li>
			<li>주소 : ${member.mem_addr} ${member.mem_addr2}</li>
			<li>가입일 : ${member.mem_date}</li>
			<c:if test="${!empty member.mem_modifydate}">
			<li>최근 정보 수정일 : ${member.mem_modifydate}</li>
			</c:if>
			<li>
				<input type="button" value="연락처 수정" onclick="location.href='modifyUserForm.do'">
			</li>
		</ul>
		<h3>비밀번호 수정</h3>
		<ul>
			<li>
				<input type="button" value="비밀번호 수정" onclick="location.href='modifyPasswordForm.do'">
			</li>
		</ul>
		<h3>상품 등록</h3>
		<ul>
			<li>
				<input type="button" value="상품 등록" onclick="location.href='${pageContext.request.contextPath}/item/itemWriteForm.do'">
			</li>
		</ul>
	</div>
	
	<!-- myhome테스트 -->
	<ul>
		<li>
					<input type="button" value="myhome" 
					onclick="location.href='${pageContext.request.contextPath}/member/myHome.do'">
		</li>
	</ul>
	
	
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅하기</title>
<%--
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css" type="text/css">
 --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css">
<style type="text/css">
   #chatting_message{
      border:1px solid black;
      width:660px;
      height:300px;
      margin:0 auto;
      padding:10px;
      overflow:auto;
   }
   .item{
      
   }
   .from-position{
      width:300px;
      margin:10px 0 10px 330px;
   }
   .from-position .item{
      background-color:yellow;
      padding:10px;
      height:50px;      
   }
   .to-position{
      width:300px;
      margin:10px 0 10px 0;
   }
   .to-position .item{
      background-color:pink;
      padding:10px;
      height:50px;
   }
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
   $(function(){
      let count=0;
      let scroll_check;
      let loop_check = true;
      $('#content').keydown(function(event){
         if(event.keyCode == 13 && !event.shiftKey) {
            $('#chatting_form').trigger('submit');
         }
      });
      
      function selectData(){
         
         $.ajax({
            url:'chattingList.do',
            type:'post',
            data:{item_num:${item.item_num},trans_num:${trans_num}},
            dataType:'json',
            cache:false,
            timeout:30000,
            success:function(param){
               if(param.result == 'logout'){
                  loop_check = false;
                  alert('로그인 후 사용하세요!');   
               }else if(param.result == 'success'){
                  if(count<param.count) scroll_check = true;
                  else scroll_check = false;
                  count = param.count;
                  
                  $('#chatting_message').empty();
                  
                  $(param.list).each(function(index,item){
                     let output = '';
                     if(item.from_num == ${user_num}){
                        output += '<div class="from-position">'+item.mem_id;
                     }else{                     
                        output += '<div class="to-position">'+item.mem_id;
                     }
                     output += '<div class="item">';
                     output += (item.chatstate_num !=0 ? '<b>①</b>' : '') + ' <span>' + item.content + '</span>';
                     output += '</div>';
                     output += '</div>';
                     
                     //문서 객체에 추가
                     $('#chatting_message').append(output);
                     if(scroll_check){
                        //스크롤를 하단으로 위치시킴
                        $('#chatting_message').scrollTop($("#chatting_message")[0].scrollHeight);
                     }
                  });   
               }else{
                  loop_check = false;
                  alert('오류가 발생했습니다!');   
               }
            },
            error:function(){
               loop_check = false;
               alert('네트워크 오류 발생');
            }
         });
         
      }
      //채팅 등록
      $('#chatting_form').submit(function(event){
         if($('#content').val().trim() == ''){
            alert('내용을 입력하세요!');
            $('#content').val('').focus();
            return false;
         }
         
         //form 이하의 태그에 입력한 데이터를 모두 읽어옴
         let form_data = $(this).serialize();
         
         //댓글 등록
         $.ajax({
            type:'post',
            data:form_data,
            url:'writeChat.do',
            dataType:'json',
            cache:false,
            timeout:30000,
            success:function(param){
               if(param.result == 'logout'){
                  alert('로그인해야 작성할 수 있습니다.');
               }else if(param.result == 'success'){
                  //폼 초기화
                  $('#content').val('').focus();
                  selectData();
               }else{
                  alert('등록시 오류 발생');
               }
            },
            error:function(){
               alert('네트워크 오류!');
            }
         });
         event.preventDefault();
      });
      selectData();
      
      setInterval(function(){
         if(!loop_check) return;
         selectData();
      },5000);
      
   });
</script>
</head>
<body>
   <jsp:include page="/WEB-INF/views/common/header.jsp"/>
   
<!-- 중앙 컨텐츠 시작 -->
	<div class="page-main-chat">
	   <c:if test="${user_num != item.mem_num}">
	   	<h2>${item.title}의 작성자 <small>${item.mem_id}</small>님과 채팅</h2>
	   </c:if>
	   <c:if test="${user_num == item.mem_num}">
	   	<h2>${trans_num}과의 대화</h2>
	   </c:if>
	  
	   <div id="chatting_message"></div>
	   
	   <form method="post" id="chatting_form">
	      <input type="hidden" name="item_num" value="${item.item_num}">
	      <c:if test="${user_num != item.mem_num}">
	      	<input type="hidden" name="to_num" value="${item.mem_num}">
	      </c:if>
	      <c:if test="${user_num == item.mem_num}">
	     	 <input type="hidden" name="to_num" value="${trans_num}">
	      </c:if>
	      <ul>
	         <li>   
	             <label for="content">내용</label>
	             <textarea rows="5" cols="40" name="content" id="content"></textarea>
	             <input type="submit" value="전송">
	         </li>
	      </ul>
	   </form>
	</div>

   <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
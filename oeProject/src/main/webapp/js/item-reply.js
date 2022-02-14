	//1. ** 페이징처리 관련 변수선언
	//9. ** 댓글목록 관련 : function selectData(pageNum){}
	//10. ** 페이지처리 이벤트 연결(다음댓글 보기 버튼 클릭시, 데이터 추가) : $('paging-button input').click(function(){}
	//5. ** 댓글 등록 : $('#re_form').submit(function(event){}	//아직목록작업을 하지않았기때문에, 정상적으로 등록되었는지 확인은 sql에서 
		//6. BoardDAO - 댓글갯수( getReplyBoardCount() )
		//7. BoardDAO - 댓글목록( getListReplyBoard() ) 
		//8. <Board>ListReplyAction
	//4. ** 댓글 작성 폼 초기화 : function initForm(){}
	//3. ** textarea에 내용 입력시 글자수 체크 : $(document).on('keyup','textarea',function(){}
	//13. ** 댓글 수정버튼 클릭시 수정폼 노출 : $(document).on('click','.modify-btn',function(){}
	//** 수정폼에서 취소버튼 클릭시 수정폼 초기화 (취소버튼은 댓글이 생성된 후에 생기는 미래의 태그 => document.on) : $(document).on('click','.re-reset',function(){}
	//		
	//** 댓글 수정폼 초기화 : function initModifyForm(){}
	 	
	//** 댓글 수정 : $(document).on('submit','#mre_form',function(event){}
		//11. BoardDAO - 댓글상세 ( getReplyBoard() )
		//12. BoardDAO - 댓글 수정 ( updateReplyBoard() )
	//** 댓글 삭제 : 댓글이 생성되어야 삭제버튼이 보임 = 미래의 태그 => document.on : $(document).on('click','.delete-btn',function(){}
	//2. ** 초기 데이터(목록) 호출 : selectData(1);
	
	
	$(function(){ 	
	//1. ** 페이징처리 관련변수 선언
	let currentPage;
	let count;
	let rowCount;
	
	
	
	//9. ** 댓글목록 관련 : function selectData(pageNum){}
 function selectData(pageNum){	//pageNum을 넘겨주면 currentPage로 지정
		//* 현재 페이지 지정 (for, paging처리)
		currentPage = pageNum;
		
		//* 로딩이미지 노출
		$('#loading').show();
		
		//* ajax통신
		$.ajax({
			type:'post',		  //board_num은 form에 hidden으로 들어가있음
			data:{pageNum:pageNum, item_num:$('#item_num').val()},	//pageNum의 데이터와 item_num의 데이터를 넘겨줌
			url:'listReply.do',
			dataType:'json',
			cache:false,
			timeout:3000,
			success:function(param){
				//* 로딩이미지 감추기
				$('#loading').hide();
				
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum==1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){	
					//출력하고 싶은 문자열을 모두 담아둘 output이라는 변수(?)를 만듦
					let output = '<div class="item">';	 
					output += '<h4>' + item.id + '</h4>';			//'id'표시
					output += '<div class = "sub-item">';
					output += '<p>' + item.re_content + '</p>';		//'댓글내용' 표시
					
					if(item.re_modifydate){	//최근 수정일이 있을경우 '최근 수정일'데이터를 출력 / 최근 수정일이 없을경우 '등록일' 데이터를 출력
						output += '<span class="modify-date">최근 수정일 : ' + item.re_modifydate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					
					//* 로그인한 회원번호와 작성자의 회원번호가 일치하는지 확인
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자 회원번호가 일치한다면
						output += ' <input type="button" data-renum="'+item.re_num+'" value="수정" class="modify-btn">';
						output += ' <input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
							//item.re_num은 custom데이터 속성임. data-renum에 댓글작성번호를 넣어서, 해당데이터로 수정 및 삭제를 편리하게 할 수 있다.
							//작성한 댓글 수정삭제하려면 item.re_num을 받아서 처리함.
					}
					
					output += '<hr size="1" noshade width="100%">';					
					output += '</div>';
					output += '</div>';
					
					//* output에 담긴 데이터를 문서객체에 추가
					$('#output').append(output);
				});
				
				//* each메서드를 빠져나온 다음 -> page button처리 연산 
				//(버튼의 생성시기 : 버튼을눌러야 페이지처리가능하기때문에, 버튼이 언제 생성되는지를 처리해야한다. )
				if(currentPage>=Math.ceil(count/rowCount)){
					//true(currentPage가 같거나 더 클 때) = 다음페이지가 없음
					$('.paging-button').hide();
				}else{
					//false = 다음페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				//json형식에 맞지않는 데이터가 들어온다거나, 네트워크 오류가 발생한 경우
				alert('네트워크 오류발생!'); //=> 발생시에는 이클립스의 콘솔 에러메시지를 보기
			}			
		});
	}
	
	
	
	//10. ** 페이지처리 이벤트 연결(다음댓글 보기 버튼 클릭시, 데이터 추가) : $('paging-button input').click(function(){}
	$('.paging-button input').click(function(){
		selectData(currentPage + 1);
	});	
	
	
	//5. ** 댓글 등록 : $('#re_form').submit(function(event){}	//아직목록작업을 하지않았기때문에, 정상적으로 등록되었는지 확인은 sql에서 
	$('#re_form').submit(function(event){			
			
		//* 내용이 비어있을 경우	
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#re_content').val().focus();
			return false;
		}	
		//* form이하의 태그에 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//* 데이터전송(by.ajax통신)
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//* 폼초기화
					initForm();
					//* 댓글작성이 성공하면 새로 입력한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함.
					selectData(1);
				}else{
					alert('등록시 오류발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
		//* 기본이벤트 제거
		event.preventDefault();
	});


	//4. ** 댓글 작성 폼 초기화 : function initForm(){}
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}	

	
	//3. ** textarea에 내용 입력시 글자수 체크 : $(document).on('keyup','textarea',function(){}
	$(document).on('keyup','textarea',function(){										
		//* 입력한 글자수를 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){	//* 300자를 넘어선 경우 => susbstring으로 문자열 잘라냄
			$(this).val($(this).val().substring(0,300));
			
		}else{	//* 300자 이하인 경우 => 몇자남았는지 알려줌 (ex. 100/300)
			let remain = 300 - inputLength;
			remain += '/300';
			
			//* 등록폼 글자수 체크	
			if($(this).attr('id')=='re_content'){	//textarea에 들어있는 id와 일치여부 확인
				$('#re_first .letter-count').text(remain);
			}else{	//수정폼 글자수 체크
				$('#mre_first .letter-count').text(remain);
			}
		}
	});	
	
	
	//13. ** 댓글 수정버튼 클릭시 수정폼 노출 : $(document).on('click','.modify-btn',function(){}
	
	
	
	//** 수정폼에서 취소버튼 클릭시 수정폼 초기화 (취소버튼은 댓글이 생성된 후에 생기는 미래의 태그 => document.on) : $(document).on('click','.re-reset',function(){}
			
			
			
	//** 댓글 수정폼 초기화 : function initModifyForm(){}
	 	
	
	
	//** 댓글 수정 : $(document).on('submit','#mre_form',function(event){}



	//** 댓글 삭제 : 댓글이 생성되어야 삭제버튼이 보임 = 미래의 태그 => document.on : $(document).on('click','.delete-btn',function(){}

	
	//2. ** 초기 데이터(목록) 호출
	selectData(1);	//1페이지 목록정보를 읽어옴
	
	});
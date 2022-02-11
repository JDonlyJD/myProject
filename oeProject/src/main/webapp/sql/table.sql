/*회원관리 부분*/
create table omember(  
   mem_num number not null primary key,
   mem_id varchar2(18) unique not null,
   mem_auth number(1) default 1 not null, /*회원등급:0 정지회원, 1:일반회원, 2.vip 3:관리자*/
);

create table omember_detail( /*상세정보*/
   mem_num number not null,
   mem_nick varchar2(18) not null,
   mem_pw varchar2(18) not null,
   --mem_auth number(1) default 1 not null,
   mem_phone varchar2(13) not null,
   mem_photo varchar2(150),
   mem_addr varchar2(100) not null,
   mem_addr2 varchar2(100) not null,
   mem_zipcode varchar2(5) not null,
   mem_email varchar2(50) not null,
   mem_date date default sysdate not null,
   mem_modifydate date,
   constraint omember_detail_pk primary key(mem_num)
);
create sequence omember_seq;



/*상품등록 테이블 영역*/
CREATE TABLE oitem(
   item_num number not null, --한글기준50자
   mem_num number not null,
   cate_num number not null,
   title varchar2(90) not null,
   price number(7) not null,
   state number(12) default 0 not null, --판매상태(0판매중/1예약중/2판매완료)
   content clob not null, --최대4기가
   filename varchar2(150),
   hit number(5) default 0 not null,
   reg_date date default SYSDATE not null,
   modify_date date,
   constraint oitem_pk primary key(item_num),
   constraint oitem_fk foreign key(mem_num) references omember(mem_num),
   constraint oitem_fk2 foreign key(cate_num) references ocategory(cate_num)
);
create sequence oitem_seq;


/*구매내역 테이블*/
create table oitem_order(
  order_num number not null,
  item_num number not null,
  mem_num number not null,
  reg_date date not null,
  constraint oitem_order_pk primary key(order_num),
  constraint oitem_order_fk foreign key(item_num) references oitem(item_num),
  constraint oitem_order_fk2 foreign key(mem_num) references omember(mem_num)
);
create sequence order_seq;



/*상품 좋아요 테이블*/
CREATE TABLE oitem_favorite(
   like_num number not null, 
   item_num number not null,
   mem_num number not null,
   constraint oitem_favorite_pk primary key(like_num),
   constraint oitem_favorite_fk foreign key(item_num) references oitem(item_num),
   constraint oitem_favorite_fk2 foreign key(mem_num) references omember(mem_num)
);

/*상품 댓글*/
create table oitem_reply(
   re_num number not null,
   item_num number not null,
   mem_num number not null,
   content varchar2(900) not null,
   reg_date date default sysdate not null,
   modify_date date,
   constraint oitem_reply_pk primary key(re_num),
   constraint oitem_reply_fk foreign key(item_num) references oitem(item_num),
   constraint oitem_reply_fk2 foreign key(mem_num) references omember(mem_num)
);
create sequence oreply_seq;



/*카테고리 테이블 영역*/
create table ocategory(
   cate_num number not null primary key,
   cate_name number(30) not null,
   cate_status number(1)
);

/*질문 문의(ask) 테이블 영역*/
CREATE TABLE oask(
   ask_num number not null,
   mem_num number not null,
   title varchar2(90) not null,
   state number(1) default 0 not null,
   kind number(1)  not null,
   content clob not null,
   reg_date date default sysdate not null,
   modify_date date,
   
   constraint oask_pk primary key(ask_num),
   constraint oask_fk foreign key(mem_num) references omember (mem_num)
);
create sequence oask_seq;

/*답변(oanswer) 테이블*/
CREATE TABLE oanswer(
   answer_num number not null,
   ask_num number not null,
   mem_num number not null,
   content varchar2(4000) not null,
   reg_date date default sysdate not null,
   modify_date date,
   
   constraint oanswer_pk primary key(answer_num),
   constraint oanswer_fk foreign key(ask_num) references oask(ask_num),
   constraint oanswer_fk2 foreign key(mem_num) references omember(mem_num)
);



/*채팅(chatting) 테이블 영역*/
CREATE TABLE ochatting(
   chat_num number not null, --채팅번호
   to_num number not null, --메시지수신번호(판매자회원번호)
   from_num number not null, --메시지발신번호(구매자회원번호)
   chatstate_num number(1) default 0 not null, --읽기상태(0읽지 않음, 1 읽음)
   content varchar2(4000) not null,
   item_num number not null,
   reg_date date default SYSDATE not null,
   constraint ochatting_pk primary key(chat_num),
    constraint ochatting_fk1 foreign key (to_num) references omember (mem_num),
    constraint ochatting_fk2 foreign key (from_num) references omember (mem_num),
    constraint ochatting_fk3 foreign key (item_num) references oitem (item_num)
);
CREATE SEQUENCE ochatting_seq;

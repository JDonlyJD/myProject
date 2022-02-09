/*회원관리 부분*/
create table omember(
   mem_num number not null primary key,
   mem_id varchar2(18) unique not null,
   mem_auth number(1) default 1 not null, /*회원등급:0 정지회원, 1:일반회원, 2.vip 3:관리자*/
);

create table omember_detail( /*상세정보*/
   mem_num number not null primary key,
   mem_nick varchar2(18) unique not null,
   mem_pw varchar2(18) not null,
   mem_auth number(1) not null,
   mem_phone varchar2(13) not null,
   mem_photo varchar2(150),
   mem_addr varchar2(100) not null,
   mem_addr2 varchar2(100) not null,
   mem_zipcode varchar2(5) not null,
   mem_email varchar2(50) not null,
   mem_date date default sysdate not null,
   mem_modifydate date
);

create sequence omember_seq;

/*상품등록 테이블 영역*/
create table oitem(
   item_num number not null primary key,
   mem_num number not null foreign key,
   cate_num number not null foreign key,
   title varchar2(90) not null,
   price number(7) not null,
   state number(12) default 0 not null,
   content clob not null,
   filename varchar2(150),
   hit number(5) not null,
   reg_date date default sysdate not null,
   modify_date date
);

create sequence oitem_seq;

/*구매내역 테이블*/
create table oitem_order(
   order_num number not null primary key,
   item_num number not null foreign key,
   mem_num number not null foreign key,
   reg_date date not null
);

/*좋아요 기능 , oitem_favorite*/
create table oitem_favorite(
   like_num number not null primary key,
   item_num number not null foreign key,
   mem_num number not null foreign key
);

/*상품 댓글*/
create table oitem_reply(
   re_num number not null primary key,
   item_num number not null foreign key,
   mem_num number not null foreign key,
   content varchar2(900) not null,
   reg_date date default sysdate not null,
   modify_date date
);

/*카테고리 테이블 영역*/
create table ocategory(
   cate_num number not null primary key,
   cate_name number(30) not null,
   cate_status number(1)
);

/*질문 문의(ask) 테이블 영역*/
create table oask(
   ask_num number not null primary key,
   mem_num number not null foreign key,
   title varchar2(90) not null,
   state number(1) not null,
   kind number(1) not null,
   content clob not null,
   reg_date date default sysdate not null,
   modify_date date
);

create sequence oack_seq;

/*답변(oanswer) 테이블*/
create table oanswer(
   answer_num number not null primary key,
   ask_num number not null foreign key,
   mem_num number not null foreign key,
   content varchar2(4000) not null,
   reg_date date default sysdate not null,
   modify_date date
);

/*댓글(reply) 테이블 영역*/
create table oreply(
   mem_num number not null primary key,
   upload_num number(100) not null foreign key,
   mem_id varchar2(18) not null foreign key,
   content clob not null,
   reg_date date default sysdate not null,
   modify_date date,
   ip varchar2(40) not null,
   re_num number not null,
);

create sequence oreply_seq;

/*채팅(chatting) 테이블 영역*/
create table ochatting(
   chat_num number not null primary key,
   to_num number not null foreign key,
   from_num number not null foreign key,
   chatstate_num number(1) default 0 not null,
   content varchar2(4000) not null,
   item_num number not null foreign key,
   reg_date date default sysdate not null
);

create sequence ochatting_seq;
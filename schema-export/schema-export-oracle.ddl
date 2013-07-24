drop table MRFAUTHORIZATION cascade constraints ;
drop table MRFDATABASE cascade constraints ;
drop table MRFFORM cascade constraints ;
drop table MRFSYSTEM cascade constraints ;
drop table MRFUPLOAD cascade constraints ;
drop table MRFUSER cascade constraints ;
create table MRFAUTHORIZATION (ID number(19,0) not null, ROLE_KEY varchar2(50 char), ROLE varchar2(50 char), USER_ID number(19,0), primary key (ID)) ;
create table MRFDATABASE (ID number(19,0) not null, NAME varchar2(255 char), DESCRIPTION varchar2(255 char), STATUS varchar2(50 char), DATABASE_TYPE varchar2(50 char), primary key (ID)) ;
create table MRFFORM (ID number(19,0) not null, REFER_FORM varchar2(50 char), POST_DATE timestamp, SERVER_TYPE_FROM varchar2(50 char), SERVER_TYPE_TO varchar2(50 char), ISDEINUP number(1,0), ISCREATETABLE number(1,0), ISCREATELOGIN number(1,0), FORM_NUMBER number(10,0), ISGRANTDATA number(1,0), ISMODIFYTABLE number(1,0), ISCREATEVIEW number(1,0), ISQUERYDATA number(1,0), ISTRANSFERDATA number(1,0), ISSOURCECODE number(1,0), REASON varchar2(255 char), START_PERFORM timestamp, END_PERFORM timestamp, PERFORM_DATE timestamp, PERFORM_REMARK varchar2(255 char), APPROVE_REMARK varchar2(255 char), APPROVE_DATE timestamp, STATUS varchar2(50 char), PROCESS_STATUS varchar2(50 char), DATABASE_ID number(19,0), SYSTEM_ID number(19,0), REQUESTER_ID number(19,0), PERFORMER_ID number(19,0), APPROVER_ID number(19,0), primary key (ID)) ;
create table MRFSYSTEM (ID number(19,0) not null, NAME varchar2(255 char), DESCRIPTION varchar2(255 char), STATUS varchar2(50 char), primary key (ID)) ;
create table MRFUPLOAD (ID number(19,0) not null, PHYNAME varchar2(200 char), LOGICNAME varchar2(200 char), CONTENTTYPE varchar2(50 char), UPLOADER varchar2(50 char), FORM_ID number(19,0), primary key (ID)) ;
create table MRFUSER (ID number(19,0) not null, USERNAME varchar2(50 char), FIRSTNAME varchar2(50 char), LASTNAME varchar2(50 char), POSITION varchar2(200 char), STATUS varchar2(50 char), ROLE_AS_STRING varchar2(100 char), primary key (ID)) ;
alter table MRFAUTHORIZATION add constraint FKCAF4DEB8A59ADE4C foreign key (USER_ID) references MRFUSER ;
alter table MRFFORM add constraint FK7928A785FF390769 foreign key (PERFORMER_ID) references MRFUSER ;
alter table MRFFORM add constraint FK7928A7854BA1374C foreign key (DATABASE_ID) references MRFDATABASE ;
alter table MRFFORM add constraint FK7928A785D853C912 foreign key (APPROVER_ID) references MRFUSER ;
alter table MRFFORM add constraint FK7928A785FC61C8FB foreign key (REQUESTER_ID) references MRFUSER ;
alter table MRFFORM add constraint FK7928A78512FB3F9D foreign key (SYSTEM_ID) references MRFSYSTEM ;
alter table MRFUPLOAD add constraint FKEB40F88285F80FAC foreign key (FORM_ID) references MRFFORM ;

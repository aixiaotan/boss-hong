CREATE DATABASE IF NOT EXISTS userdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE userdb;
/*
�����û�����
*/
DROP TABLE IF EXISTS userMain;

CREATE TABLE userMain (
  userId 			varchar(50)  	NOT NULL 									
	COMMENT '�û���id',
  username 			varchar(50)  	NOT NULL 									
	COMMENT '�û���',
  password 			varchar(200) 	NOT NULL 									
	COMMENT '�û�����',
  groupId 			varchar(50)  	NULL		DEFAULT NULL 					
	COMMENT '�û�������ID',
  userType 			varchar(10)  	NULL		DEFAULT NULL 					
	COMMENT '�û�����',
  createtime 		datetime 		NOT NULL		
	COMMENT '����ʱ��',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '������',
  updatetime 		datetime 		NOT NULL	
	COMMENT '����ʱ��',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '������',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '�����Ƿ���Ч',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '����',
	
  PRIMARY KEY (userid)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '�û�����' ;
/*
�����û���Ϣ��
*/
DROP TABLE IF EXISTS userInfo;

CREATE TABLE userInfo (
  userId 			varchar(50)  	NOT NULL
	COMMENT '�û���id',
  email 			varchar(100)  	NULL 		DEFAULT NULL 							
	COMMENT '�û���',
  mobilePhone		varchar(20) 	NULL 		DEFAULT NULL 							
	COMMENT '�û�����',
  realname 			varchar(50)  	NULL		DEFAULT NULL 					
	COMMENT '�û�������ID'
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '�û���Ϣ��' ;

/*
�����û�����
*/
DROP TABLE IF EXISTS userGroup;

CREATE TABLE userGroup (
  groupId 			varchar(50)  	NOT NULL 									
	COMMENT '����id',
  groupName			varchar(50)  	NOT NULL 									
	COMMENT '����',
  level 			varchar(5) 		NOT NULL 	
	COMMENT '�鼶�𣬿�������չȨ���鹦��',
  createtime 		datetime 		NOT NULL		
	COMMENT '����ʱ��',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '������',
  updatetime 		datetime 		NOT NULL	
	COMMENT '����ʱ��',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '������',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '�����Ƿ���Ч',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '����',
	
  PRIMARY KEY (groupid)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '�û�����' ;

/*
�����û������Ȩ�޹�����
*/
DROP TABLE IF EXISTS groupAuthorityAssociative;

CREATE TABLE groupAuthorityAssociative (
  groupId 			varchar(50)  	NOT NULL 									
	COMMENT '����id',
  authorityId		varchar(50)  	NOT NULL 									
	COMMENT 'Ȩ�ޱ�id'
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '�û������Ȩ�޹�����' ;

/*
����Ȩ�ޱ�
*/
DROP TABLE IF EXISTS authority;

CREATE TABLE authority (
  authorityId 		varchar(50)  	NOT NULL 									
	COMMENT 'Ȩ�ޱ�id',
  serviceId			varchar(50)  	NOT NULL 									
	COMMENT '΢����ķ�����',
  resouce 			varchar(300) 	NOT NULL 	
	COMMENT '��Դ·�����������Ϊ�ӿ�·��',
  authorityType 	varchar(20) 	NOT NULL		
	COMMENT 'Ȩ�����ͣ��������Ϊ��Դ·����Ӧ������',
  allowsAuthorized 	BOOLEAN  		NOT NULL	DEFAULT FALSE	
	COMMENT '�Ƿ�������Ȩ�����ֶ���Ȩ�޲����������Ȩ',
  createtime 		datetime 		NOT NULL		
	COMMENT '����ʱ��',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '������',
  updatetime 		datetime 		NOT NULL	
	COMMENT '����ʱ��',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '������',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '�����Ƿ���Ч',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '����',
	
  PRIMARY KEY (authorityId)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = 'Ȩ�ޱ�' ;

CREATE DATABASE IF NOT EXISTS userdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE userdb;
/*
创建用户主表
*/
DROP TABLE IF EXISTS userMain;

CREATE TABLE userMain (
  userId 			varchar(50)  	NOT NULL 									
	COMMENT '用户表id',
  username 			varchar(50)  	NOT NULL 									
	COMMENT '用户名',
  password 			varchar(200) 	NOT NULL 									
	COMMENT '用户密码',
  groupId 			varchar(50)  	NULL		DEFAULT NULL 					
	COMMENT '用户所在组ID',
  userType 			varchar(10)  	NULL		DEFAULT NULL 					
	COMMENT '用户类型',
  createtime 		datetime 		NOT NULL		
	COMMENT '创建时间',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '创建人',
  updatetime 		datetime 		NOT NULL	
	COMMENT '更新时间',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '更新人',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '数据是否有效',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '描述',
	
  PRIMARY KEY (userid)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '用户主表' ;
/*
创建用户信息表
*/
DROP TABLE IF EXISTS userInfo;

CREATE TABLE userInfo (
  userId 			varchar(50)  	NOT NULL
	COMMENT '用户表id',
  email 			varchar(100)  	NULL 		DEFAULT NULL 							
	COMMENT '用户名',
  mobilePhone		varchar(20) 	NULL 		DEFAULT NULL 							
	COMMENT '用户密码',
  realname 			varchar(50)  	NULL		DEFAULT NULL 					
	COMMENT '用户所在组ID'
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '用户信息表' ;

/*
创建用户组别表
*/
DROP TABLE IF EXISTS userGroup;

CREATE TABLE userGroup (
  groupId 			varchar(50)  	NOT NULL 									
	COMMENT '组别表id',
  groupName			varchar(50)  	NOT NULL 									
	COMMENT '组名',
  level 			varchar(5) 		NOT NULL 	
	COMMENT '组级别，可用于扩展权限组功能',
  createtime 		datetime 		NOT NULL		
	COMMENT '创建时间',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '创建人',
  updatetime 		datetime 		NOT NULL	
	COMMENT '更新时间',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '更新人',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '数据是否有效',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '描述',
	
  PRIMARY KEY (groupid)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '用户组别表' ;

/*
创建用户组别与权限关联表
*/
DROP TABLE IF EXISTS groupAuthorityAssociative;

CREATE TABLE groupAuthorityAssociative (
  groupId 			varchar(50)  	NOT NULL 									
	COMMENT '组别表id',
  authorityId		varchar(50)  	NOT NULL 									
	COMMENT '权限表id'
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '用户组别与权限关联表' ;

/*
创建权限表
*/
DROP TABLE IF EXISTS authority;

CREATE TABLE authority (
  authorityId 		varchar(50)  	NOT NULL 									
	COMMENT '权限表id',
  serviceId			varchar(50)  	NOT NULL 									
	COMMENT '微服务的服务名',
  resouce 			varchar(300) 	NOT NULL 	
	COMMENT '资源路径，可以理解为接口路径',
  authorityType 	varchar(20) 	NOT NULL		
	COMMENT '权限类型，可以理解为资源路径对应的类型',
  allowsAuthorized 	BOOLEAN  		NOT NULL	DEFAULT FALSE	
	COMMENT '是否允许授权，部分顶级权限不允许进行授权',
  createtime 		datetime 		NOT NULL		
	COMMENT '创建时间',
  createname 		varchar(50)  	NOT NULL	
	COMMENT '创建人',
  updatetime 		datetime 		NOT NULL	
	COMMENT '更新时间',
  updatename 		varchar(50)  	NOT NULL	
	COMMENT '更新人',
  valid 			INT		  		NOT NULL	DEFAULT	1
	COMMENT '数据是否有效',
  remark 			varchar(200)  	NULL		DEFAULT NULL
	COMMENT '描述',
	
  PRIMARY KEY (authorityId)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT = '权限表' ;

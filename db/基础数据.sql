USE userdb;
/*修改数据库安全级别，进行基础数据时放开权限*/
SET SQL_SAFE_UPDATES = 0;

/*插入 usermain 表默认数据*/
DELETE FROM usermain;

/*
INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','admin','111111','1','1',SYSDATE(),'admin',SYSDATE(),'admin',1,'');
*/
INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','admin','111111','1','1','2017-08-12 22:54:04','admin','2017-08-12 22:54:04','admin',1,'');

INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark)
VALUES ('2','xq','222222','1','1','2017-08-12 22:54:04','admin','2017-08-12 22:54:04','admin',1,'');

/*插入 usergroup 表默认数据*/
DELETE FROM usergroup;

/*
INSERT INTO userdb.usergroup (groupId, groupName, level, createtime, createname, updatetime, updatename, valid, remark) 
VALUES ('1', 'ADMIN', '1', SYSDATE(), 'admin', SYSDATE(), 'admin', 1, '');
*/
INSERT INTO usergroup (groupId,groupName,level,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','ADMIN','1','2017-08-12 23:30:13','admin','2017-08-12 23:30:13','admin',1,'');


/*结束时将数据库安全级别调回*/
SET SQL_SAFE_UPDATES = 1;
USE userdb;
/*�޸����ݿⰲȫ���𣬽��л�������ʱ�ſ�Ȩ��*/
SET SQL_SAFE_UPDATES = 0;

/*���� usermain ��Ĭ������*/
DELETE FROM usermain;

/*
INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','admin','111111','1','1',SYSDATE(),'admin',SYSDATE(),'admin',1,'');
*/
INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','admin','111111','1','1','2017-08-12 22:54:04','admin','2017-08-12 22:54:04','admin',1,'');

INSERT INTO usermain (userid,username,password,groupid,userType,createtime,createname,updatetime,updatename,valid,remark)
VALUES ('2','xq','222222','1','1','2017-08-12 22:54:04','admin','2017-08-12 22:54:04','admin',1,'');

/*���� usergroup ��Ĭ������*/
DELETE FROM usergroup;

/*
INSERT INTO userdb.usergroup (groupId, groupName, level, createtime, createname, updatetime, updatename, valid, remark) 
VALUES ('1', 'ADMIN', '1', SYSDATE(), 'admin', SYSDATE(), 'admin', 1, '');
*/
INSERT INTO usergroup (groupId,groupName,level,createtime,createname,updatetime,updatename,valid,remark) 
VALUES ('1','ADMIN','1','2017-08-12 23:30:13','admin','2017-08-12 23:30:13','admin',1,'');


/*����ʱ�����ݿⰲȫ�������*/
SET SQL_SAFE_UPDATES = 1;
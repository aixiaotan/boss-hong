
create table `tb_fdb_login_user` (
		  `id` int(11) primary key not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
      `updated_time` datetime not null comment '修改时间',
		  `wechat_id` varchar(50) not null comment '登陆微信账号',
		  `wechat_user_info` varchar(1000) default null comment '微信用户信息',
		  `login_phone_number` varchar(11) default null comment '登录手机号',
		  key `wechat_id` (`wechat_id`)
		) engine=innodb auto_increment=1 default charset=utf8  comment '微信绑定信息表';

		create table `tb_fdb_shop` (
		  `id` int(11) primary key   not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
		  `updated_time` datetime not null comment '修改时间',
		  `login_user_id` int(11) default null comment '微信绑定信息表主键',
		  `shop_name` varchar(40) default null comment '店铺名称',
		  `user_name` varchar(40) not null comment '姓名',
		  `city_code` varchar(6) not null comment '所在城市',
		  `company_name` varchar(200) not null comment '公司名称',
		  `wechat_id` varchar(50) default null comment '别人联系您的微信账号',
		  `phone_number` varchar(11) default null comment '别人联系您的手机号码',
		  `is_publicity` varchar(1) not null comment '是否公开微信账号和手机号码，1-公开，0-不公开。',
			`transpond_times` int(8) default 0 comment '店铺转发次数',
		  key `phone_number` (`phone_number`),
		  key `wechat_id` (`wechat_id`),
		  key `login_user_id` (`login_user_id`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '名片信息表';

		create table `tb_fdb_label` (
		  `id` int(11) primary key not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
		  `updated_time` datetime not null comment '修改时间',
		  `shop_id` int(11) not null comment '店铺主键',
		  `shop_label` varchar(20) default null comment '店铺标签',
		  key `shop_label` (`shop_label`),
		  key `shop_id` (`shop_id`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '店铺标签表';

		create table `tb_fdb_outline` (
		  `id` int(11) primary key   not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
		  `updated_time` datetime not null comment '修改时间',
		  `shop_id` int(11) not null comment '店铺表主键',
		  `outline_content` varchar(250) not null comment '大纲内容',
			key `shop_id` (`shop_id`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '店铺的产品大纲表';


		create table `tb_fdb_visit_record` (
		  `id` int(11) primary key   not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
      `updated_time` datetime not null comment '修改时间',
		  `visit_type` varchar(2) not null comment '访问类型，01-店铺，02-大纲',
		  `relation_id` int(11) not null comment '关联店铺或者大纲的主键',
		  `wechat_id` varchar(50) default null comment '查看该店铺或者大纲的用户微信号',
		  key `visit_type` (`visit_type`),
		  key `relation_id` (`relation_id`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '热度统计表，用于统计查看店铺或者大纲的次数。';


		create table `tb_fdb_favorite` (
		  `id` int(11) primary key   not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
      `updated_time` datetime not null comment '修改时间',
		  `login_user_id` integer(11) default null comment '微信绑定信息表主键',
		  `favorite_type` varchar(2) not null comment '收藏类型，01-店铺，02-名片，03-大纲。',
		  `relation_id` int(11) not null comment '关联主键',
		  `delivered_to_me` varchar(2) not null comment '是否投递给我的，01-投递给我的，02-我收藏的',
			`deliver_user` integer(11) default null comment '投递人userID',
		  key `relation_id` (`relation_id`),
			key `deliver_user` (`deliver_user`),
		  key `login_user_id` (`login_user_id`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '我的收藏表';


		create table `tb_fdb_document_info` (
		  `id` int(11) primary key   not null auto_increment comment '主键',
		  `created_time` datetime not null comment '创建时间',
		  `updated_time` datetime not null comment '修改时间',
		  `group_code` varchar(30) not null comment '文件属组，outline-产品大纲',
		  `file_name` varchar(100) default null comment '文件名',
		  `file_remark` varchar(400) default null comment '文件备注',
			`file_size` int(5) default null comment '文件大小，单位：字节',
		  `row_no` int(5) not null comment '文件顺序',
		  `file_path` varchar(200) not null comment '文件存储地址',
		  `id_foreign_key` int(11) default null comment '关联表主键',
		  key `group_code` (`group_code`),
			key `id_foreign_key` (`id_foreign_key`)
		) engine=innodb auto_increment=1 default charset=utf8 comment '附件表';



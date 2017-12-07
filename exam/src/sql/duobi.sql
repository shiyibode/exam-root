-- 主页上的小组
DROP TABLE IF EXISTS t_main_page_group;
CREATE TABLE t_main_page_group
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  text VARCHAR(128) COMMENT '小组标题',
  icon VARCHAR(256) DEFAULT NULL COMMENT '图标所在位置',
  available BOOLEAN COMMENT '是否启用',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE
);

-- 格子内小标题
DROP TABLE IF EXISTS t_main_page_group_item;
CREATE TABLE t_main_page_group_item
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  group_id BIGINT COMMENT '格子内小标题所属的组',
  text VARCHAR(64) COMMENT '格子内小标题名称',
  text_describe VARCHAR(128) COMMENT '小标题的二次描述',
  icon VARCHAR(256) DEFAULT NULL COMMENT '图标所在位置',
  route VARCHAR(256) COMMENT '要跳转的小程序页面',
  available BOOLEAN COMMENT '是否启用',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 实体用户
DROP TABLE IF EXISTS t_unit;
CREATE TABLE t_unit
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  title TEXT COMMENT '标题',
  name VARCHAR(32) COMMENT '名称',
  type INT COMMENT '类型，1-个人 2-个体 3-企业 4-行政单位 5-事业单位',
  identity_type INT COMMENT '证件类型',
  identity_number VARCHAR(128) COMMENT '证件号码',
  cover_image VARCHAR(256) COMMENT '封面图片路径',
  birth_place VARCHAR(128) COMMENT '籍贯',
  authentication_status VARCHAR(32) COMMENT '认证类型',
  service_years INT COMMENT '工龄、从业年限',
  service_area VARCHAR(512) COMMENT '服务地区、服务范围',
  main_phone_number VARCHAR(64) COMMENT '主联系方式，默认使用此联系方式' NOT NULL,
  regist_time TIMESTAMP COMMENT '注册日期',
  service_content_text TEXT COMMENT '服务内容描述',
  service_content_video VARCHAR(256) COMMENT '介绍视频路径',
  service_content_video_poster VARCHAR(256) COMMENT '介绍视频的封面',
  introduction TEXT COMMENT '自我介绍文字内容',
  have_introduction BOOLEAN COMMENT '是否有自我介绍',
  string_tags VARCHAR(512) COMMENT '用逗号分隔的标签内容',
  look_over_times INT COMMENT '被浏览次数',
  focused_times INT COMMENT '被关注次数',
  zaned_times INT COMMENT '被点赞次数',
  have_address BOOLEAN COMMENT '是否有固定地址，有就展现地图',
  available BOOLEAN COMMENT '是否启用',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 位置信息表
DROP TABLE IF EXISTS t_location;
CREATE TABLE t_location
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  unit_id BIGINT COMMENT '所属的实体',
  address VARCHAR(256) COMMENT '文字描述的地址',
  longitude NUMERIC(13,10) COMMENT '经度',
  latitude NUMERIC(13,10) COMMENT '纬度',
  scale INT COMMENT '缩放级别，5-18',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 位置上标记信息表
DROP TABLE IF EXISTS t_marker;
CREATE TABLE t_marker
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  location_id BIGINT COMMENT '所属的location',
  icon_path VARCHAR(256) COMMENT '该标记的图标路径',
  longitude NUMERIC(13,10) COMMENT '经度',
  latitude NUMERIC(13,10) COMMENT '纬度',
  width FLOAT COMMENT '图标宽度',
  height FLOAT COMMENT '图标高度',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 实体与图片关联表
DROP TABLE IF EXISTS t_unit_related_image;
CREATE TABLE t_unit_related_image
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  unit_id BIGINT COMMENT '所属的实体id',
  description VARCHAR(256) COMMENT '图片描述',
  image_path VARCHAR(256) COMMENT '图片路径',
  width FLOAT COMMENT '宽度',
  height FLOAT COMMENT '高度',
  type INT COMMENT '类型 1-网络，2-本地',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 客户关注实体关联表
DROP TABLE IF EXISTS t_client_focused_unit;
CREATE TABLE t_client_focused_unit
(
  open_id VARCHAR(64) COMMENT '客户openid',
  unit_id BIGINT COMMENT '实体id',
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (open_id,unit_id)
);

-- 客户点赞实体关联表
DROP TABLE IF EXISTS t_client_zaned_unit;
CREATE TABLE t_client_zaned_unit
(
  open_id VARCHAR(64) COMMENT '客户openid',
  unit_id BIGINT COMMENT '实体id',
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (open_id,unit_id)
);

-- 主页小标题与实体关联表
DROP TABLE IF EXISTS t_main_page_group_item_unit;
CREATE TABLE t_main_page_group_item_unit
(
  main_page_group_item_id BIGINT COMMENT '小标题id',
  unit_id BIGINT COMMENT '实体id',
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (main_page_group_item_id,unit_id)
);

-- 系统用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user
(
  id BIGINT AUTO_INCREMENT,
  user_name VARCHAR(64) COMMENT '登录名',
  password VARCHAR(256) COMMENT '密码',
  name VARCHAR(64) COMMENT '姓名',
  age INT COMMENT '年龄',
  identity_type INT COMMENT '证件类型',
  identity_number VARCHAR(64) COMMENT '证件号码',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

-- 客户联系信息表
DROP TABLE IF EXISTS t_contact_info;
CREATE TABLE t_contact_info
(
  id BIGINT AUTO_INCREMENT,
  type VARCHAR(64) COMMENT '类型名称，如手机、QQ、微信',
  is_phone_number BOOLEAN COMMENT '是否是电话号码',
  is_QQ_number BOOLEAN COMMENT '是否是QQ号码',
  is_we_chat_number BOOLEAN COMMENT '是否是微信号码',
  value VARCHAR(64) COMMENT '号码值',
  icon VARCHAR(256) COMMENT '图标路径，图标需和type对应',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE,

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_unit_contact_info;
CREATE TABLE t_unit_contact_info(
  unit_id BIGINT,
  contact_info_id BIGINT,

  PRIMARY KEY (unit_id,contact_info_id)
);

DROP TABLE IF EXISTS t_hot_unit;
CREATE TABLE t_hot_unit(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  unit_id BIGINT COMMENT '个人或店铺id',
  sort INT COMMENT '排序，多个热门在页面上的排序,默认应在程序里设置为id+100' UNIQUE,
  available BOOLEAN COMMENT '是否启用该热门',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE
);

DROP TABLE IF EXISTS t_recent_online_unit;
CREATE TABLE t_recent_online_unit(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sort INT,
  unit_id BIGINT,
  available BOOLEAN COMMENT '是否启用该热门',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE
);

DROP TABLE IF EXISTS t_mine_contact_number;
CREATE TABLE t_mine_contact_number(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sort INT,
  phone_number VARCHAR(32),
  available BOOLEAN COMMENT '是否启用该号码',
  create_by BIGINT COMMENT '创建人id',
  create_time TIMESTAMP COMMENT '创建时间' DEFAULT now(),
  update_by BIGINT COMMENT '更新人',
  update_time TIMESTAMP COMMENT '最近更新时间' DEFAULT now(),
  del_flag BOOLEAN COMMENT '删除标志' DEFAULT FALSE
);
-- t_main_page_group_item 表注释
# ALTER TABLE t_main_page_group_item MODIFY COLUMN id BIGINT COMMENT '主键id';
# ALTER TABLE t_main_page_group_item ADD INDEX t_main_page_group_item_id_idx(id);

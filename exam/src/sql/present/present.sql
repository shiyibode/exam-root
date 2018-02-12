-- 礼品种类登记表
DROP TABLE IF EXISTS t_present_type;
CREATE TABLE t_present_type
(
  id BIGSERIAL NOT NULL,
  org_code VARCHAR (10),
  distribute_type VARCHAR (1) DEFAULT '2',
  present_name VARCHAR(30),
  start_date DATE,
  end_date DATE,
  remarks VARCHAR(256),
  create_by INTEGER,
  create_time TIMESTAMP NOT NULL DEFAULT now(),
  update_by INTEGER,
  update_time TIMESTAMP,
  del_flag BOOLEAN NOT NULL DEFAULT FALSE ,
  PRIMARY KEY (id)
);
-- t_present_type 表注释
COMMENT ON TABLE t_present_type IS '礼品种类登记表';
COMMENT ON SEQUENCE t_present_type_id_seq IS '礼品种类登记表主键序列';
-- t_present_type 表字段注释
COMMENT ON COLUMN t_present_type.id IS '编号';
COMMENT ON COLUMN t_present_type.org_code IS '礼品所属机构';
COMMENT ON COLUMN t_present_type.present_name IS '礼品名称';
COMMENT ON COLUMN t_present_type.distribute_type IS '领取类型：1-所有人均可领取 2-指定人员领取，默认为指定人员领取';
COMMENT ON COLUMN t_present_type.start_date IS '开始日期';
COMMENT ON COLUMN t_present_type.end_date IS '结束周期';
COMMENT ON COLUMN t_present_type.remarks IS '备注信息';
COMMENT ON COLUMN t_present_type.create_by IS '创建者';
COMMENT ON COLUMN t_present_type.create_time IS '创建时间';
COMMENT ON COLUMN t_present_type.update_by IS  '更新者';
COMMENT ON COLUMN t_present_type.update_time IS '更新时间';
COMMENT ON COLUMN t_present_type.del_flag IS '删除标志';

-- 可领取礼品人员名单表
DROP TABLE IF EXISTS t_present_customer;
CREATE TABLE t_present_customer
(
  id BIGSERIAL NOT NULL,
  present_id BIGINT,
  customer_name VARCHAR(128),
  identity_number VARCHAR(18),
  distribute_flag VARCHAR(1),
  org_code VARCHAR(10),
  organization_id BIGINT,
  account_no VARCHAR(22),
  phone_number VARCHAR(20),
  remarks VARCHAR(256),
  create_by INTEGER,
  create_time TIMESTAMP NOT NULL DEFAULT now(),
  update_by INTEGER,
  update_time TIMESTAMP,
  del_flag BOOLEAN NOT NULL DEFAULT FALSE ,
  PRIMARY KEY (id)
);

-- t_present_customer 表注释
COMMENT ON TABLE t_present_customer IS '可领取礼品人员名单表';
COMMENT ON SEQUENCE t_present_customer_id_seq IS '礼品种类登记表主键序列';
-- t_present_customer 表字段注释
COMMENT ON COLUMN t_present_customer.id IS '编号';
COMMENT ON COLUMN t_present_customer.present_id IS '礼品编号';
COMMENT ON COLUMN t_present_customer.customer_name IS '客户名称';
COMMENT ON COLUMN t_present_customer.identity_number IS '身份证号码';
COMMENT ON COLUMN t_present_customer.distribute_flag IS '是否领用：0-未领取，1-已领取';
COMMENT ON COLUMN t_present_customer.org_code IS '领取机构';
COMMENT ON COLUMN t_present_customer.organization_id IS '为客户领取礼品的柜员当时所在的机构';
COMMENT ON COLUMN t_present_customer.remarks IS '备注信息';
COMMENT ON COLUMN t_present_customer.create_by IS '创建者';
COMMENT ON COLUMN t_present_customer.create_time IS '创建时间';
COMMENT ON COLUMN t_present_customer.update_by IS  '更新者';
COMMENT ON COLUMN t_present_customer.update_time IS '更新时间';
COMMENT ON COLUMN t_present_customer.del_flag IS '删除标志';

-- INSERT INTO t_present_customer (present_id, customer_name, identity_number, distribute_flag, org_code, organization_id, remarks, create_by, create_time, update_by, update_time, del_flag) VALUES (16, 'syb', '152728199109263913', '0', '78054', null, null, 1, now(), 1, now(), FALSE );
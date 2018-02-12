-- 账户登记表
DROP TABLE IF EXISTS t_bldk_non_performing_loan;
CREATE TABLE t_bldk_non_performing_loan
(
  id BIGSERIAL NOT NULL,
  org_code VARCHAR(10),
  account_no VARCHAR(128),
  customer_name VARCHAR(128),
  principal NUMERIC(17,2),
  interest NUMERIC(17,2),
  compound_interest NUMERIC(17,2),
  fx_rate NUMERIC(17,4),
  hx_date DATE,
  interest_term VARCHAR(1),
  before_hx_interest NUMERIC(17,2) DEFAULT 0,
  remarks VARCHAR(1024),
  create_by INTEGER,
  create_time TIMESTAMP NOT NULL DEFAULT now(),
  update_by INTEGER,
  update_time TIMESTAMP,
  del_flag BOOLEAN NOT NULL DEFAULT FALSE ,
  PRIMARY KEY (id)
);
-- t_bldk_non_performing_loan 表注释
COMMENT ON TABLE t_bldk_non_performing_loan IS '不良贷款信息登记表';
COMMENT ON SEQUENCE t_bldk_non_performing_loan_id_seq IS '不良贷款信息登记表主键序列';
-- t_bldk_non_performing_loan 表字段注释
COMMENT ON COLUMN t_bldk_non_performing_loan.id IS '编号';
COMMENT ON COLUMN t_bldk_non_performing_loan.org_code IS '所属机构';
COMMENT ON COLUMN t_bldk_non_performing_loan.account_no IS '贷款帐号';
COMMENT ON COLUMN t_bldk_non_performing_loan.customer_name IS '客户名称';
COMMENT ON COLUMN t_bldk_non_performing_loan.principal IS '贷款核销时本金';
COMMENT ON COLUMN t_bldk_non_performing_loan.interest IS '贷款核销时利息';
COMMENT ON COLUMN t_bldk_non_performing_loan.compound_interest IS '贷款核销时复利';
COMMENT ON COLUMN t_bldk_non_performing_loan.fx_rate IS '罚息利率';
COMMENT ON COLUMN t_bldk_non_performing_loan.hx_date IS '核销日期';
COMMENT ON COLUMN t_bldk_non_performing_loan.interest_term IS '结息周期';
COMMENT ON COLUMN t_bldk_non_performing_loan.before_hx_interest IS '核销前最后一次结息时的利息总金额，用于计算复利';
COMMENT ON COLUMN t_bldk_non_performing_loan.remarks IS '备注信息';
COMMENT ON COLUMN t_bldk_non_performing_loan.create_by IS '创建者';
COMMENT ON COLUMN t_bldk_non_performing_loan.create_time IS '创建时间';
COMMENT ON COLUMN t_bldk_non_performing_loan.update_by IS  '更新者';
COMMENT ON COLUMN t_bldk_non_performing_loan.update_time IS '更新时间';
COMMENT ON COLUMN t_bldk_non_performing_loan.del_flag IS '删除标志';
CREATE INDEX ON t_bldk_non_performing_loan(account_no);

-- 还款记录表
DROP TABLE IF EXISTS t_bldk_repayment_record;
CREATE TABLE t_bldk_repayment_record
(
  id BIGSERIAL NOT NULL,
  non_performing_loan_id BigINT,
  repayment_principal NUMERIC(17,2),
  repayment_interest NUMERIC(17,2),
  repayment_compound_interest NUMERIC(17,2),
  repayment_date DATE,
  remarks VARCHAR(1024),
  create_by INTEGER,
  create_time TIMESTAMP NOT NULL DEFAULT now(),
  update_by INTEGER,
  update_time TIMESTAMP,
  del_flag BOOLEAN NOT NULL DEFAULT FALSE ,
  PRIMARY KEY (id)
);
-- 表注释
COMMENT ON TABLE t_bldk_repayment_record IS '还款登记表';
COMMENT ON SEQUENCE t_bldk_repayment_record_id_seq IS '不良贷款还款登记表主键序列';
-- 表字段注释
COMMENT ON COLUMN t_bldk_repayment_record.id IS '编号';
COMMENT ON COLUMN t_bldk_repayment_record.non_performing_loan_id IS '贷款信息id';
COMMENT ON COLUMN t_bldk_repayment_record.repayment_principal IS '本次所还本金';
COMMENT ON COLUMN t_bldk_repayment_record.repayment_interest IS '本次所还利息';
COMMENT ON COLUMN t_bldk_repayment_record.repayment_compound_interest IS '本次所还复利';
COMMENT ON COLUMN t_bldk_repayment_record.repayment_date IS '还款日期';
COMMENT ON COLUMN t_bldk_repayment_record.remarks IS '备注信息';
COMMENT ON COLUMN t_bldk_repayment_record.create_by IS '创建者';
COMMENT ON COLUMN t_bldk_repayment_record.create_time IS '创建时间';
COMMENT ON COLUMN t_bldk_repayment_record.update_by IS  '更新者';
COMMENT ON COLUMN t_bldk_repayment_record.update_time IS '更新时间';
COMMENT ON COLUMN t_bldk_repayment_record.del_flag IS '删除标志';

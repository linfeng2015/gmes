drop table if exists gm_task;
drop table if exists gm_user;
drop table if exists gm_recaddr;
drop table if exists gm_email;
drop table if exists gm_report;
drop table if exists gm_temp;

create table gm_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table gm_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;
CREATE TABLE gm_recaddr (
  company VARCHAR(32) ,
  name VARCHAR(32)  NULL,
  addr VARCHAR(64)  NULL,
  id BIGINT(20) NOT NULL DEFAULT 0,
  flag INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB;
CREATE TABLE gm_email (
  title VARCHAR(32)  DEFAULT NULL,
  content TEXT,
  id BIGINT(20) NOT NULL DEFAULT 0,
  attach VARCHAR(64)  DEFAULT NULL
  send_time DATETIME DEFAULT NULL,
  dsend_time DATETIME DEFAULT NULL,
  report_id BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB ;
CREATE TABLE gm_report(
  id BIGINT(20) NOT NULL,
  email_id BIGINT(20) DEFAULT NULL,
  open_num INTEGER(11) DEFAULT NULL,
  click_num INTEGER(11) DEFAULT NULL,
  fail_num INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB;
CREATE TABLE gm_temp (
  id BIGINT(20) NOT NULL DEFAULT 0,
  tmpkind VARCHAR(32)  DEFAULT NULL,
  `name` VARCHAR(32)  DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB;
CREATE TABLE gm_addrsheet(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB
AUTO_INCREMENT=1 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
;
CREATE TABLE gm_sheetrec(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  recaddr_id BIGINT(20) DEFAULT NULL,
  addrsheet_id BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB
AUTO_INCREMENT=1 ROW_FORMAT=FIXED CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
;

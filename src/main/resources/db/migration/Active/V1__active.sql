create table activ (
 id bigint not null auto_increment,
 description varchar(200) not null,
 start_time datetime(6) not null,
 type varchar(20) not null,
   created_at datetime(6),
   updated_at datetime(6),
 primary key (id)) engine=InnoDB;

create table a_labels (
 activity_id bigint not null,
  label varchar(255)) engine=InnoDB

create table act_id_gen (
 id_gen varchar(255) not null,
  id_val bigint,
  primary key (id_gen)) engine=InnoDB;

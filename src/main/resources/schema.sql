set foreign_key_checks = 0;
drop table if exists asset, asset_history, borrow_history, hardware, hardware_group, person, room;
set foreign_key_checks = 1;


create table asset (
   id integer not null auto_increment,
	uuid BINARY(36),
	deleted bit not null,
	note varchar(255),
	asset_name varchar(255),
	serial_number varchar(255),
	asset_history_id integer not null,
	hardware_id integer not null,
	primary key (id)
) engine=InnoDB;

create table asset_history (
   id integer not null auto_increment,
	disposal_date datetime(6),
	purchase_date datetime(6) not null,
	warranty_date datetime(6),
	primary key (id)
) engine=InnoDB;

create table borrow_history (
   id integer not null auto_increment,
	borrow_date datetime(6) not null,
	borrow_until datetime(6),
	note varchar(255),
	return_back_date datetime(6),
	asset_id integer not null,
	person_id integer not null,
	room_id integer not null,
	primary key (id)
) engine=InnoDB;

create table hardware (
   id integer not null auto_increment,
	type varchar(255) not null,
	hardware_group_id integer not null,
	primary key (id)
) engine=InnoDB;

create table hardware_group (
   id integer not null auto_increment,
	hw_group varchar(255) not null,
	primary key (id)
) engine=InnoDB;

create table person (
   id integer not null auto_increment,
	email varchar(255),
	enabled bit,
	name varchar(255),
	password varchar(255),
	role varchar(255) not null,
	surname varchar(255),
	primary key (id)
) engine=InnoDB;

create table room (
   id integer not null auto_increment,
	deleted bit not null,
	number integer not null,
	type varchar(255) not null,
	primary key (id)
) engine=InnoDB;


alter table person 
   add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email);

alter table asset 
   add constraint FKjlkwjj0dsa4n9vmebgpqk0kqj 
   foreign key (asset_history_id) 
   references asset_history (id);

alter table asset 
   add constraint FKeyu6io4wohffstaccx2xbti4w 
   foreign key (hardware_id) 
   references hardware (id);

alter table borrow_history 
   add constraint FKin5lfuqa06cxmy8mr51fp3nmo 
   foreign key (asset_id) 
   references asset (id);

alter table borrow_history 
   add constraint FK9mp4rx1f79oebc3h2ceiasr6r 
   foreign key (person_id) 
   references person (id);

alter table borrow_history 
   add constraint FKsayjix2x4ol3n0yo1gy1ja43b 
   foreign key (room_id) 
   references room (id);

alter table hardware 
   add constraint FKdh2ctexahajitslcsfqgwsl0u 
   foreign key (hardware_group_id) 
   references hardware_group (id);
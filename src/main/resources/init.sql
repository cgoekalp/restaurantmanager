
CREATE TABLE IF NOT EXISTS PRODUCT(
id int auto_increment PRIMARY KEY not null,
name varchar(255) not null,
description varchar(255),
bruttoprice double not null,
nettoprice double not null,
created timestamp not null,
updatetime timeStamp not null,
category varchar(255) not null,
tax int not null,
deleted boolean default false
);


CREATE TABLE IF NOT EXISTS ORDERED(
id int auto_increment PRIMARY KEY not null,
total double not null,
ordertime timestamp not null,
diningtable int not null,
ordernumber int not null unique,
paymenttype varchar(255) not null,
orderstatus int not null
);


CREATE TABLE IF NOT EXISTS ORDEREDPRODUCT(
id int auto_increment PRIMARY KEY not null,
pid INT NOT NULL,
invoice timestamp,
name varchar(255) not null,
bruttoprice double not null,
nettoprice double not null,
category varchar(255) not null,
tax int not null,
oid INT NOT NULL,
productnumber int not null,
FOREIGN KEY (pid) REFERENCES PRODUCT(id),
FOREIGN KEY (oid) REFERENCES ORDERED(id)
);

CREATE TABLE IF NOT EXISTS ORDERNUMBER(
id int auto_increment (1000, 2) not null
);
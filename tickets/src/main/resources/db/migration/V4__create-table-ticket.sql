
CREATE TABLE TICKET (
	ID bigint auto_increment primary key, 
    title varchar(100) not null,
    description text not null,
    client_id bigint not null,
    status VARCHAR(30) not null,
    priority VARCHAR(75) not null,
	date_start DATETIME not null,
	date_end DATETIME,
    FOREIGN KEY (client_id) REFERENCES CLIENT(ID)
);
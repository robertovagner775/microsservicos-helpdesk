CREATE TABLE DURATION_TICKET (
	id CHAR(36) PRIMARY KEY,
    id_ticket INT not null,
    DESCRIPTION TEXT not null,
    date_change datetime not null,
    type_change varchar(50) not null,
    category_id char(36),
    FOREIGN KEY (category_id) REFERENCES CATEGORY(ID)
);
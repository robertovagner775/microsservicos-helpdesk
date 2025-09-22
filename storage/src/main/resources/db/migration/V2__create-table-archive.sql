CREATE TABLE ARCHIVE(
    id CHAR(36) PRIMARY KEY,
    filekey VARCHAR(75) NOT NULL,
    filename VARCHAR(75) NOT NULL,
    filetype VARCHAR(50) NOT NULL,
    bucket VARCHAR(50) NOT NULL,
    id_ticket BIGINT,
    FOREIGN KEY (id_ticket) REFERENCES file_ticket(id_ticket)
)
CREATE TABLE ticket_technical (
    ticket_id BIGINT,
    technical_id BIGINT,
    FOREIGN KEY (ticket_id) REFERENCES TICKET(ID),
    FOREIGN KEY (technical_id) REFERENCES TECHNICAL(id),
    PRIMARY KEY(ticket_ID, technical_ID)
)
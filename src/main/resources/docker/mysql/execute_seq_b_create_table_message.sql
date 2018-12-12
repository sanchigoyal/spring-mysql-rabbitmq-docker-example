USE message;
create table message (
	id int NOT NULL AUTO_INCREMENT,
    sender varchar(100),
    receiver  varchar(100),
    timestamp timestamp,
    message_text varchar(1000),
    PRIMARY KEY (id)
);
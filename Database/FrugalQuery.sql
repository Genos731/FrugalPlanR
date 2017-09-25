INSERT INTO account (username, password, email)
VALUES ("Antoine", "Antoine", "Antoine@email.com");

INSERT INTO account (username, password, email)
VALUES ("Betsy", "Betsy", "Betsy@email.com");

INSERT INTO account (username, password, email)
VALUES ("Cockless", "Cockless", "Cockless@email.com");

INSERT INTO budget (goalValue, startDate, endDate, account_id)
VALUES (10, "2017-01-01", "2017-12-31", 1);

INSERT INTO budget (goalValue, startDate, endDate, account_id)
VALUES (69, "2017-04-01", "2017-06-01", 2);

INSERT INTO budget (goalValue, startDate, endDate, account_id)
VALUES (2657, "2017-06-26", "2017-12-16", 2);

INSERT into transaction (value, item, date, account_id)
VALUES (102, "Penis", "2017-01-06", 3);

INSERT into transaction (value, item, date, account_id)
VALUES (12.56, "4chan membership", "2017-02-12", 1);

INSERT into transaction (value, item, date, account_id)
VALUES (1087.44, "Stripper", "2017-03-09", 1);

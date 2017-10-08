INSERT INTO repeating (type)
VALUES ("DAILY");

INSERT INTO repeating (type)
VALUES ("WEEKLY");

INSERT INTO repeating (type)
VALUES ("FORTNIGHTLY");

INSERT INTO repeating (type)
VALUES ("MONTHLY");


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

INSERT INTO category (name, account_id)
VALUES ("Food", 1);

INSERT INTO category (name, account_id)
VALUES ("Rent", 1);

INSERT INTO category (name, account_id)
VALUES ("Transport", 2);


INSERT INTO transaction (isIncome, value, date, description, location, category_id, account_id)
VALUES (true, 70, "2017-01-06", "Groceries", "Sydney", 1, 1);

INSERT INTO transaction (isIncome, value, date, description, location, repeating_id, category_id, account_id)
VALUES (0, 329, "2015-03-22", "Rent Money", "Sydney", 1, 1, 1);

INSERT INTO transaction (isIncome, value, date, description, location, repeating_id, category_id, account_id)
VALUES (1, 69, "2016-02-02", "train money", "Melbourne", 1, 2, 2);
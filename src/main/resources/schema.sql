CREATE TABLE IF NOT EXISTS account (
    acc_no INT PRIMARY KEY,
    acc_holder VARCHAR(255) NOT NULL,
    balance DOUBLE NOT NULL,
    acc_type VARCHAR(20) NOT NULL,
    interest_rate DOUBLE,
    overdraft INT
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    t_type VARCHAR(50),
    amount DOUBLE,
    from_acc INT,
    to_acc INT,
    timestamp TIMESTAMP
);
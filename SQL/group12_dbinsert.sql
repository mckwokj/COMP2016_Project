CREATE TABLE Student(
	snum INTEGER,
	name CHAR(20),
	gender CHAR(1),
	major CHAR(4),
	discount_level REAL,
	PRIMARY KEY (snum));

-- INSERT INTO Student VALUES(12345678, 'Kurt', 'M', 'COMP', NULL);
-- INSERT INTO Student VALUES(23456789, 'Rex', 'M', 'GBUS', NULL);
-- INSERT INTO Student VALUES(34567890, 'Jerry', 'F', 'NURS', NULL);
-- INSERT INTO Student VALUES(45678901, 'Mary', 'F', 'NURS', NULL);

CREATE TABLE Place_Order(
	order_no CHAR(10),
	student INTEGER NOT NULL,
	order_date DATE,
	books_ordered VARCHAR(100),
	total_price REAL,
	payment_method CHAR(10),
	card_no CHAR(19),
	PRIMARY KEY (order_no),
	FOREIGN KEY (student) REFERENCES Student(snum));

-- INSERT INTO Place_Order VALUES('ORD_000001', 12345678, TO_DATE('15/04/2020:15:20:08','dd/mm/yyyy:hh24/mi/ss'), 'Harry Potter I', 300, 'CASH', NULL);
-- INSERT INTO Place_Order VALUES('ORD_000002', 23456789, TO_DATE('15/04/2020:14:20:00','dd/mm/yyyy:hh24/mi/ss'), 'Harry Potter II', 300, 'CASH', NULL);
-- INSERT INTO Place_Order VALUES('ORD_000003', 12345128, TO_DATE('15/04/2020:15:20:08','dd/mm/yyyy:hh24/mi/ss'), 'Harry Potter III', 400, 'CARD', '1234-5678-1234-5678');  *** integrity constraint violated

CREATE TABLE Book(
	bnum CHAR(10),
	title CHAR(30),
	author CHAR(20),
	price REAL,
	amount INTEGER,
	PRIMARY KEY(bnum));

-- INSERT INTO Book VALUES('BOOK_00001', 'Harry Potter I', 'J.K Rowling', 300, 10);
-- INSERT INTO Book VALUES('BOOK_00002', 'Harry Potter II', 'J.K Rowling', 300, 10);
-- INSERT INTO Book VALUES('BOOK_00003', 'Harry Potter III', 'J.K Rowling', 400, 20);

CREATE TABLE Deliver(
	order_no CHAR(10),
	bnum CHAR(10),
	delivery_date DATE,
	PRIMARY KEY(order_no, bnum),
	FOREIGN KEY (order_no) REFERENCES Place_Order,
	FOREIGN KEY (bnum) REFERENCES Book);

-- INSERT INTO Deliver VALUES('ORD_000001', 'BOOK_00001', NULL);
-- INSERT INTO Deliver VALUES('ORD_000003', 'BOOK_00003', NULL); *** integrity constraint violated





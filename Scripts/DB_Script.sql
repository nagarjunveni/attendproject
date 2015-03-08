
-- users table consists of the list of users who can access the application
CREATE TABLE ATT_USERS(
	USERNAME varchar2(10),
	PASSWORD varchar2(10),
	FIRSTNAME varchar2(30),
	LASTNAME varchar2(30),
	ROLE varchar2(10),
	ACTIVE NUMBER(1),
	CONSUMERID NUMBER
	);

--att_pers table is used to store the master records of person whom swipes needs to be tracked.
create table att_person(
	UNIQUENO number,
	FIRSTNAME VARCHAR2(20),
	LASTNAME VARCHAR2(20),
	CONTACTNO NUMBER(11),
	RFIDNUM NUMBER(14),
	CONSUMERID NUMBER
);

--ATT_STUDENTS table is used to store the master records of students whose swipes needs to be tracked.
create table ATT_STUDENTS(
 ROLLNO   NUMBER,
 FIRSTNAME  VARCHAR2(20),
 LASTNAME   VARCHAR2(20),
 CONTACTNO  NUMBER(11),
 CLASS      VARCHAR2(5),
 RFIDNUM    NUMBER(14)
);

--att_person_swipes is used to store the swipes of person or student
crete table ATT_PERSON_SWIPES(
	UNIQUENO NUMBER,
	SWIPETIME TIMESTAMP(6),
	MACHINEID VARCHAR2(6),
	SWIPEDATE DATE,
	CONSUMERID NUMBER
);

--ATT_PERSON_ABSENTEES is used to store the absentees.
create table ATT_PERSON_ABSENTEES(
	UNIQUENO NUMBER,
	SWIPEDATE DATE
);

--ATT_PERSON_SWIPES_SUMMARY is used to store the summary of the swipes (contains login and logout time).
create table ATT_PERSON_SWIPES_SUMMARY(
	UNIQUENO    NUMBER,
	LOGINTIME   TIMESTAMP(6),
	LOGOUTTIME  TIMESTAMP(6),
	LOGINMACHINEID  VARCHAR2(6),
	LOGOUTMACHINEID VARCHAR2(6),
	SWIPEDATE  DATE,
	CONSUMERID  NUMBER
);




	
	
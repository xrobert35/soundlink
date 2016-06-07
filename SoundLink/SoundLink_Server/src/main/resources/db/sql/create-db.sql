drop table if exists users;
create table users (
  username VARCHAR(255) PRIMARY KEY,
  password VARCHAR(255),
  email VARCHAR(255),
  role VARCHAR(255)
);

insert into users values('ixoh','$2a$10$w.o1pHkP4iU..KEGvwHq.u.ULBAVILB1d9t0uFVQzFSEXmflsUc.i','ixoh35@gmail.com','ADMIN');
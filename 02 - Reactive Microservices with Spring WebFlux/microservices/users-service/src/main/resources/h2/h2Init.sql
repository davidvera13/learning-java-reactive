create table users(
    id bigint auto_increment,
    name varchar(50),
    balance long,
    primary key (id)
);

create table transactions(
  id bigint auto_increment,
  user_id bigint,
  amount double,
  transaction_date timestamp,
  primary key (id),
  foreign key (user_id) references users(id) on delete cascade
);


insert into users(name, balance)
values
    ('Fox Mulder', 325),
    ('Dana Scully', 1762),
    ('Walter Skinner', 525),
    ('Monica Reyes', 272),
    ('Dummy 5th', 124),
    ('John Dogget', 1810);

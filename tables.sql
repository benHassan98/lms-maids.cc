create database library;

use library;

create table if not exists books(
    id integer primary key auto_increment,
    title varchar(280) not null,
    author varchar(50) not null,
    publication_year integer not null,
    isbn varchar(50) unique not null,
    is_borrowed bit not null
);

create table if not exists patrons(
    id integer primary key auto_increment,
    name varchar(50) not null,
    contact_info varchar(80) not null
);

create table if not exists borrowing_records(
    id integer primary key auto_increment,
    book_id integer not null,
    patron_id integer not null,
    borrowing_date timestamp null default null,
    return_date timestamp null default null,
    foreign key(book_id) references books(id) on delete cascade,
    foreign key(patron_id) references patrons(id) on delete cascade
);

COMMIT;
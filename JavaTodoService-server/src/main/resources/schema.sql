  
create table if not exists todos
(
   id int not null auto_increment,
   title varchar(255) not null, 
   completed boolean not null,
   primary key(id)
);

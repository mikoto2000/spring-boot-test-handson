drop table if exists "user";

create table "user" (
  id bigserial primary key,
  name text,
  birthday date
);

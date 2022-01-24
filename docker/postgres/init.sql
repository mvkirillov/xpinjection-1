create user xpinjection with password 'myPassword';
create schema xpinjection;
grant xpinjection to postgres;
alter schema xpinjection owner to xpinjection;
GRANT CONNECT ON DATABASE xpinjection TO xpinjection;
GRANT USAGE ON SCHEMA xpinjection TO xpinjection;

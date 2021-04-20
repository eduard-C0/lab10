create table pet
(
    id           integer          not null
        constraint pet_pkey
            primary key,
    name         varchar(100)     not null,
    species      varchar(100)     not null,
    sellingprice double precision not null
);

alter table pet
    owner to postgres;

INSERT INTO public.pet (id, name, species, sellingprice) VALUES (0, 'yyy', 'rrr', 100);
INSERT INTO public.pet (id, name, species, sellingprice) VALUES (1, 'qqq', 'qqq', 200);
INSERT INTO public.pet (id, name, species, sellingprice) VALUES (6, 'jo', 'dog', 1234);
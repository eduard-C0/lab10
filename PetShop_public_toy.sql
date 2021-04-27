create table toy
(
    id       serial not null
        constraint toy_pk
            primary key,
    name     varchar,
    species  varchar,
    price    double precision,
    material varchar
);

alter table toy
    owner to postgres;

INSERT INTO public.toy (id, name, species, price, material) VALUES (1, 'a', 'a', 100, 'a');
INSERT INTO public.toy (id, name, species, price, material) VALUES (2, 'b', 'b', 50, 'b');
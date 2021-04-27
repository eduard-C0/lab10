create table food
(
    id          serial not null
        constraint food_pk
            primary key,
    name        varchar,
    species     varchar,
    description varchar,
    price       double precision
);

alter table food
    owner to postgres;

INSERT INTO public.food (id, name, species, description, price) VALUES (1, 'whiskas ', 'cat', 'description1', 50);
INSERT INTO public.food (id, name, species, description, price) VALUES (2, 'royal ', 'dog', 'description2', 140);
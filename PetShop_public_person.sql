create table person
(
    id     integer      not null
        constraint person_pkey
            primary key,
    name   varchar(100) not null,
    budget double precision
);

alter table person
    owner to postgres;

INSERT INTO public.person (id, name, budget) VALUES (4, 'yoy', 123);
INSERT INTO public.person (id, name, budget) VALUES (5, 'uuu', 2000);
INSERT INTO public.person (id, name, budget) VALUES (9, 'iii', 3000);
INSERT INTO public.person (id, name, budget) VALUES (0, 'ttt', 900);
INSERT INTO public.person (id, name, budget) VALUES (1, 'coco', 133);
INSERT INTO public.person (id, name, budget) VALUES (2, 'po', 543);
INSERT INTO public.person (id, name, budget) VALUES (3, 'ion', 555);
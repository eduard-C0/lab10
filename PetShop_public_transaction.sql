create table transaction
(
    id     integer not null
        constraint transaction_pkey
            primary key,
    pet    integer
        constraint transaction_pet_fkey
            references pet,
    person integer
        constraint transaction_person_fkey
            references person
);

alter table transaction
    owner to postgres;

INSERT INTO public.transaction (id, pet, person) VALUES (0, 1, 1);
INSERT INTO public.transaction (id, pet, person) VALUES (1, 0, 3);
INSERT INTO public.transaction (id, pet, person) VALUES (8, 6, 5);
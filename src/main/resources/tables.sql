create table public.location
(
    id          integer default nextval('locations_id_seq'::regclass) not null
        constraint locations_pkey
            primary key,
    longitude   double precision                                      not null,
    latitude    double precision                                      not null,
    description varchar
);

alter table public.location
    owner to postgres;


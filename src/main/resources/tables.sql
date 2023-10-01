create schema public;

create sequence public.locations_id_seq
    as integer;

create table public.location
(
    id          integer default nextval('public.locations_id_seq'::regclass) not null
        constraint locations_pkey
            primary key,
    longitude   double precision                                      not null,
    latitude    double precision                                      not null,
    description varchar
);

alter sequence public.locations_id_seq owned by location.id;

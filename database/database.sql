create schema if not exists public;

create sequence if not exists public.locations_id_seq
    as integer;

create table if not exists public.location
(
    id          integer default nextval('public.locations_id_seq'::regclass) not null
        constraint locations_pkey
            primary key,
    longitude   double precision                                      not null,
    latitude    double precision                                      not null,
    description varchar
);

alter sequence if exists public.locations_id_seq owned by location.id;

create sequence locations_id_seq
    as integer;

alter sequence locations_id_seq owner to postgres;

create table location
(
    id          integer default nextval('locations_id_seq'::regclass) not null
        constraint locations_pkey
            primary key,
    longitude   double precision                                      not null,
    latitude    double precision                                      not null,
    description varchar
);

alter table location
    owner to postgres;

alter sequence locations_id_seq owned by location.id;

create table beers (
    id bigint not null,
    brewery_id bigint,
    name varchar(255),
    cat_id bigint,
    style_id bigint,
    abv varchar(255),
    ibu varchar(255),
    srm varchar(255),
    upc varchar(255),
    filepath varchar(255),
    descript text,
    add_user varchar(255),
    last_mod timestamp(6) with time zone,
    primary key (id)
);

create table breweries (
    id bigint not null,
    name varchar(255),
    address1 varchar(255),
    address2 varchar(255),
    city varchar(255),
    state varchar(255),
    code varchar(255),
    country varchar(255),
    phone varchar(255),
    website varchar(255),
    filepath varchar(255),
    descript text,
    add_user bigint,
    last_mod timestamp(6) with time zone,
    primary key (id)
);

create table categories (
    id bigint not null,
    cat_name varchar(255),
    last_mod timestamp(6) with time zone,
    primary key (id)
);

create table geocodes (
    id bigint not null,
    brewery_id bigint,
    latitude numeric(17,14),
    longitude numeric(18,14),
    accuracy varchar(255),
    primary key (id)
);

create table styles (
    id bigint not null,
    cat_id bigint,
    style_name varchar(255),
    last_mod timestamp(6) with time zone,
    primary key (id)
);

COPY beers FROM '/tmp/beers.csv' DELIMITER ',' CSV HEADER;
COPY breweries FROM '/tmp/breweries.csv' DELIMITER ',' CSV HEADER;
COPY categories FROM '/tmp/categories.csv' DELIMITER ',' CSV HEADER;
COPY geocodes FROM '/tmp/geocodes.csv' DELIMITER ',' CSV HEADER;
COPY styles FROM '/tmp/styles.csv' DELIMITER ',' CSV HEADER;


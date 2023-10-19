DELETE FROM geocodes
WHERE NOT EXISTS (
    SELECT 1 FROM breweries WHERE breweries.id = geocodes.brewery_id
);

alter table if exists geocodes
   add constraint FK6tbleaoj9y2q1i99qw0qwp2ju
   foreign key (brewery_id)
   references breweries;

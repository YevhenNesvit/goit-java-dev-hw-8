create table manufacturers
(
	manufacturer_id UUID PRIMARY KEY,
    manufacturer_name VARCHAR(200) NOT NULL UNIQUE
);

alter table manufacturers owner to postgres;

create table products
(
	product_id UUID PRIMARY KEY,
	product_name VARCHAR(200) NOT NULL,
	price DECIMAL,
	manufacturer_id UUID,
    	FOREIGN KEY (manufacturer_id)
            REFERENCES manufacturers
);

alter table products owner to postgres;
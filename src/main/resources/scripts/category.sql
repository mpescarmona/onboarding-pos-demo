-- Table: category

-- DROP TABLE category;

CREATE TABLE category
(
  id integer NOT NULL,
  categoryname character varying(80),
  CONSTRAINT pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE category
  OWNER TO postgres;


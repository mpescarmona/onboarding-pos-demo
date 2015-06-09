-- Table: customer

-- DROP TABLE customer;

CREATE TABLE customer
(
  id integer NOT NULL,
  firstname character varying(80),
  lastname character varying(80),
  phonenumber character varying(80),
  email character varying(80),
  CONSTRAINT pk_customer PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE customer
  OWNER TO postgres;


-- Table: article

-- DROP TABLE article;

CREATE TABLE article
(
  id integer NOT NULL,
  categoryid integer NOT NULL,
  name character varying(80),
  description character varying(200),
  price numeric(10,2),
  inventory integer NOT NULL,
  CONSTRAINT pk_article PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE article
  OWNER TO postgres;


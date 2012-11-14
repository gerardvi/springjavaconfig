CREATE ROLE tpowner WITH login password 'tpowner';
ALTER database tp owner TO tpowner;
CREATE SEQUENCE hibernate_sequence;
GRANT ALL ON hibernate_sequence TO tpowner;
CREATE TABLE words (
  id bigserial,
  word_type varchar (8),
  code varchar (128) NOT NULL,
  CONSTRAINT pk_words PRIMARY KEY (id),
  CONSTRAINT uk_words_code UNIQUE (code)
);
GRANT ALL ON words TO tpowner;
INSERT INTO words (id, word_type, code) VALUES (4294967296, 'N', 'boek');
INSERT INTO words (id, word_type, code) VALUES (4294967297, 'N', 'lengte');
INSERT INTO words (id, word_type, code) VALUES (4294967298, 'V', 'lezen');
INSERT INTO words (id, word_type, code) VALUES (4294967299, 'V', 'meten');
INSERT INTO words (id, word_type, code) VALUES (4294967300, 'A', 'lang');
INSERT INTO words (id, word_type, code) VALUES (4294967301, 'A', 'mooi');
INSERT INTO words (id, word_type, code) VALUES (4294967302, 'A', 'goed');
INSERT INTO words (id, word_type, code) VALUES (4294967303, 'P', 'in');
INSERT INTO words (id, word_type, code) VALUES (4294967304, 'P', 'met');

CREATE SEQUENCE hibernate_sequence;
CREATE ROLE tpowner;
CREATE TABLE words (
  id bigint,
  word_type varchar (8),
  code varchar (128) NOT NULL,
  CONSTRAINT pk_words PRIMARY KEY (id),
  CONSTRAINT uk_words_code UNIQUE (code)
);
INSERT INTO words (id, word_type, code) VALUES (4294967296, 'N', 'krant');
INSERT INTO words (id, word_type, code) VALUES (4294967297, 'N', 'oppervlakte');
INSERT INTO words (id, word_type, code) VALUES (4294967298, 'V', 'schrijven');
INSERT INTO words (id, word_type, code) VALUES (4294967299, 'V', 'berekenen');
INSERT INTO words (id, word_type, code) VALUES (4294967300, 'A', 'kort');
INSERT INTO words (id, word_type, code) VALUES (4294967301, 'A', 'lelijk');
INSERT INTO words (id, word_type, code) VALUES (4294967302, 'A', 'slecht');
INSERT INTO words (id, word_type, code) VALUES (4294967303, 'P', 'op');
INSERT INTO words (id, word_type, code) VALUES (4294967304, 'P', 'zonder');
INSERT INTO words (id, word_type, code) VALUES (4294967305, 'V', 'vallen');
INSERT INTO words (id, word_type, code) VALUES (4294967306, 'V', 'opstaan');
INSERT INTO words (id, word_type, code) VALUES (4294967307, 'P', 'in');
INSERT INTO words (id, word_type, code) VALUES (4294967308, 'P', 'met');
INSERT INTO words (id, word_type, code) VALUES (4294967309, 'P', 'bij');

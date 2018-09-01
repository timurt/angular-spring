INSERT INTO roles (id, key, name, is_deleted) VALUES (1, 'ADMIN', 'Administrator', false);
INSERT INTO roles (id, key, name, is_deleted) VALUES (2, 'OWNER', 'Owner', false);
INSERT INTO roles (id, key, name, is_deleted) VALUES (3, 'CLIENT', 'Client', false);

INSERT INTO users (id, login, password, name, role_id, is_deleted) VALUES
  (1, 'admin', '$2a$11$YqcRWHl9Fr1mnTZgzkGUUeHtcZkwwB7KKL8HpQGD8FJw.W4zupNTu', 'Administrator', 1, false);

INSERT INTO users (id, login, password, name, role_id, is_deleted) VALUES
  (2, 'realtor', '$2a$11$fm7II.hPEEuFwTQaklQ2N.4c6SQzkS1Zi6rdGon5X9pYlEWCmKBjS', 'Realtor', 2, false);
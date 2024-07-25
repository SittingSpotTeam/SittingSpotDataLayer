CREATE TABLE sitting_spot
(
    id TEXT             NOT NULL,
    x  DOUBLE PRECISION NOT NULL,
    y  DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_sitting_spot PRIMARY KEY (id)
);

CREATE TABLE sitting_spot_labels
(
    id     TEXT NOT NULL,
    labels VARCHAR(255)
);

CREATE TABLE sitting_spot_tags
(
    id    TEXT NOT NULL,
    key   OID,
    value OID
);

ALTER TABLE sitting_spot_labels
    ADD CONSTRAINT fk_sitting_spot_labels_on_sitting_spot FOREIGN KEY (id) REFERENCES sitting_spot (id);

ALTER TABLE sitting_spot_tags
    ADD CONSTRAINT fk_sitting_spot_tags_on_sitting_spot FOREIGN KEY (id) REFERENCES sitting_spot (id);
--DROP TABLE IF EXISTS note;
-- ----------------------------
-- Table structure for note
-- ----------------------------
CREATE TABLE note (
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    content varchar(500) NOT NULL,
    createddate datetime,
    lastmodifieddate datetime
);
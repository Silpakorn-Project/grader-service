CREATE TABLE IF NOT EXISTS problems (
    problem_id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    difficulty ENUM('EASY','MEDIUM','HARD') NOT NULL,
    type ENUM('MATH','DATA_STRUCTURE','GRAPH','STRING') NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (problem_id)
);

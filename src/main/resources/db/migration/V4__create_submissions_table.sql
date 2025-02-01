CREATE TABLE IF NOT EXISTS submissions (
    submission_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    problem_id BIGINT NOT NULL,
    code LONGTEXT NOT NULL,
    language ENUM('JAVA', 'PYTHON', 'C') NOT NULL,
    status ENUM('Passed', 'Failed') NOT NULL,
    score_percent DECIMAL(5,2) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (submission_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (problem_id) REFERENCES problems(problem_id)
);
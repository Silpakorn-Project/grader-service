CREATE TABLE IF NOT EXISTS testcases (
    testcase_id BIGINT NOT NULL AUTO_INCREMENT,
    problem_id BIGINT NOT NULL,
    input_data VARCHAR(255) NOT NULL,
    expected_output VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (testcase_id),
    FOREIGN KEY (problem_id) REFERENCES problems(problem_id)
);
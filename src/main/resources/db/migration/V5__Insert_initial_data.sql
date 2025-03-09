INSERT INTO problems (problem_id, title, description, difficulty, type, created_at, updated_at)
VALUES
    (1, 'Sum of Two Numbers', 'Write a program to find the sum of two numbers.', 'EASY', 'MATH', NOW(), NOW()),
    (2, 'String Reversal', 'Write a program to reverse a string.', 'MEDIUM', 'STRING', NOW(), NOW());

INSERT INTO testcases (problem_id, input_data, expected_output, created_at, updated_at)
VALUES
    (1, '3 5', '8', NOW(), NOW()),
    (1, '10 20', '30', NOW(), NOW()),
    (1, '-1 1', '0', NOW(), NOW()),
    (1, '0 0', '0', NOW(), NOW()),
    (2, 'hello', 'olleh', NOW(), NOW()),
    (2, 'world', 'dlrow', NOW(), NOW());

INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$.YJ1FvGeZrqlHDpMyO5jieh/JLZyb9h8wMVYU8IJLYU9WjMg3BbDe', 'admin@gmail.com'),
       ('admin2', '$2a$10$.YJ1FvGeZrqlHDpMyO5jieh/JLZyb9h8wMVYU8IJLYU9WjMg3BbDe', 'admin2@gmail.com');

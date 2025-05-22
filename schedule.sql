-- users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       registered_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL
);

-- schedules
CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           task TEXT NOT NULL,
                           user_id BIGINT NOT NULL,
                           created_at DATETIME NOT NULL,
                           updated_at DATETIME NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- default users
INSERT INTO users (name, email, registered_at, updated_at) VALUES
                                                               ('김진현', 'kimjh@example.com', NOW(), NOW()),
                                                               ('이진수', 'binary@example.com', NOW(), NOW());
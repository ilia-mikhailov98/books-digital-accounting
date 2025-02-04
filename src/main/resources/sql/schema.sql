CREATE TABLE IF NOT EXISTS Person (
    id SERIAL PRIMARY KEY,
    FullName VARCHAR(30) NOT NULL CHECK (LENGTH(FullName) <= 40),
    BirthYear INT NOT NULL CHECK (BirthYear >= 1)
);

CREATE TABLE IF NOT EXISTS Book (
    id SERIAL PRIMARY KEY,
    Name VARCHAR NOT NULL,
    Author VARCHAR NOT NULL,
    Year INT NOT NULL CHECK(Year >= 1),
    PersonId INT,
    FOREIGN KEY (PersonId) REFERENCES Person(id) ON DELETE SET NULL
);
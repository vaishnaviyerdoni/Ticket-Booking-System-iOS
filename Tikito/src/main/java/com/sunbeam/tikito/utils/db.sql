-- ============================================
-- TIKITO DATABASE
-- Compatible with existing Spring Boot Project
-- ============================================

DROP DATABASE IF EXISTS tikito_db;
CREATE DATABASE tikito_db;
USE tikito_db;

-- ============================================
-- USERS
-- ============================================

CREATE TABLE users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,

    first_name VARCHAR(80),
    last_name VARCHAR(80),

    birth_date DATE,

    email VARCHAR(80) NOT NULL UNIQUE,

    image_name VARCHAR(255),

    password VARCHAR(200) NOT NULL,

    phone CHAR(10) UNIQUE,

    role VARCHAR(20) NOT NULL
        CHECK(role IN ('ROLE_USER','ROLE_ADMIN')),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- EVENTS
-- ============================================

CREATE TABLE events(

    event_id INT PRIMARY KEY AUTO_INCREMENT,

    event_name VARCHAR(100) NOT NULL,

    event_type VARCHAR(100) NOT NULL,

    event_description TEXT,

    event_duration_min INT
        CHECK(event_duration_min > 0),

    age_restriction INT
        CHECK(age_restriction >= 0),

    poster_url VARCHAR(500) NOT NULL,
     poster_public_id VARCHAR(500),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- VENUES
-- ============================================

CREATE TABLE venues(

    venue_id INT PRIMARY KEY AUTO_INCREMENT,

    venue_name VARCHAR(80) NOT NULL,

    venue_address VARCHAR(250),

    seat_capacity INT
        CHECK(seat_capacity > 0),

    are_facilities_available BOOLEAN NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- SEATS
-- ============================================

CREATE TABLE seats(

    seat_id INT PRIMARY KEY AUTO_INCREMENT,

    venue_id INT NOT NULL,

    seat_no VARCHAR(15) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_seat_venue
        FOREIGN KEY (venue_id)
        REFERENCES venues(venue_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT uk_seat_per_venue
        UNIQUE(venue_id, seat_no)
);

-- ============================================
-- SHOWS
-- ============================================

CREATE TABLE shows(

    show_id INT PRIMARY KEY AUTO_INCREMENT,

    venue_id INT NOT NULL,

    event_id INT NOT NULL,

    price DECIMAL(10,2) NOT NULL
        CHECK(price > 0),

    is_eighteen_plus BOOLEAN NOT NULL,

    show_date DATE NOT NULL,

    show_start_time TIME NOT NULL,

    show_end_time TIME NOT NULL,

    show_language VARCHAR(15),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_show_venue
        FOREIGN KEY (venue_id)
        REFERENCES venues(venue_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_show_event
        FOREIGN KEY (event_id)
        REFERENCES events(event_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ============================================
-- BOOKINGS
-- ============================================

CREATE TABLE bookings(

    booking_id INT PRIMARY KEY AUTO_INCREMENT,

    user_id INT NOT NULL,

    show_id INT NOT NULL,

    total_amt DECIMAL(10,2) NOT NULL
        CHECK(total_amt >= 0),

    payment_status VARCHAR(25) NOT NULL,

    booking_status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_user
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_booking_show
        FOREIGN KEY(show_id)
        REFERENCES shows(show_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ============================================
-- BOOKED SEATS
-- ============================================

CREATE TABLE booked_seats(

    booked_seat_id INT PRIMARY KEY AUTO_INCREMENT,

    booking_id INT NOT NULL,

    show_id INT NOT NULL,

    seat_id INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_show_seat
        UNIQUE(show_id, seat_id),

    CONSTRAINT fk_booked_booking
        FOREIGN KEY(booking_id)
        REFERENCES bookings(booking_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_booked_show
        FOREIGN KEY(show_id)
        REFERENCES shows(show_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_booked_seat
        FOREIGN KEY(seat_id)
        REFERENCES seats(seat_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ============================================
-- OLD MOVIES
-- ============================================

CREATE TABLE old_movies(

    old_movie_id INT PRIMARY KEY AUTO_INCREMENT,

    old_movie_name VARCHAR(80),

    old_movie_release_year YEAR,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);



-- ============================================
-- EVENT POSTERS
-- ============================================

CREATE TABLE event_posters(

    event_poster_id INT PRIMARY KEY AUTO_INCREMENT,

    event_id INT NOT NULL,

    event_poster_name VARCHAR(250),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_poster_event
        FOREIGN KEY(event_id)
        REFERENCES events(event_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ============================================
-- foods
-- ============================================

CREATE TABLE foods (
    food_id INT PRIMARY KEY AUTO_INCREMENT,

    food_name VARCHAR(100) NOT NULL,

    description VARCHAR(300),

    image_url VARCHAR(500) NOT NULL,

    price DECIMAL(10,2) NOT NULL,

    is_available BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- otp_verification
-- ============================================

CREATE TABLE otp_verification (
    otp_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    otp INT NOT NULL,
    purpose VARCHAR(50) NOT NULL,
    expiry_time DATETIME NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- REVIEWS
-- ============================================

CREATE TABLE revues(

    revues_id INT PRIMARY KEY AUTO_INCREMENT,

    old_movie_id INT,

    user_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_movie
        FOREIGN KEY(old_movie_id)
        REFERENCES old_movies(old_movie_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_review_user
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
CREATE TABLE books
(
    BOOK_ID         INT AUTO_INCREMENT PRIMARY KEY,
    BOOK_TITLE      VARCHAR(255)                NOT NULL,
    BOOK_SYNOPSIS   TEXT                        NULL,
    BOOK_PRICE      DECIMAL(10, 2) DEFAULT 0.00 NULL,
    BOOK_QUANTITY   INT            DEFAULT 0    NULL,
    BOOK_IMAGE_PATH VARCHAR(255)                NULL
);

CREATE TABLE TAGS (
                      TAG_ID   INT AUTO_INCREMENT PRIMARY KEY,
                      TAG_NAME VARCHAR(100)
);

CREATE TABLE BOOK_TAGS (
                           BOOK_ID INT,
                           TAG_ID  INT,
                           PRIMARY KEY (BOOK_ID, TAG_ID),
                           FOREIGN KEY (BOOK_ID) REFERENCES books(BOOK_ID) ON DELETE CASCADE,
                           FOREIGN KEY (TAG_ID) REFERENCES TAGS(TAG_ID) ON DELETE CASCADE
);




CREATE TABLE REVIEWS (
                         USER_ID            INT,
                         BOOK_ID            INT,
                         REVIEW_TITLE       VARCHAR(100),
                         REVIEW_RATING      TINYINT CHECK (REVIEW_RATING >= 0 AND REVIEW_RATING <= 5),
                         REVIEW_DESCRIPTION VARCHAR(1000),
                         REVIEW_CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_user FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID),
                         CONSTRAINT fk_book FOREIGN KEY (BOOK_ID) REFERENCES books(BOOK_ID)
);
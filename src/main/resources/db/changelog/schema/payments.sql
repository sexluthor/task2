CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    ref VARCHAR,
    category_id BIGINT,
    user_id VARCHAR,
    recipient_id VARCHAR,
    desc_id VARCHAR,
    amount FLOAT
);

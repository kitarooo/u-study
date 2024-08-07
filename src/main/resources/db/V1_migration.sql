insert into users(firstname, email, password, role, phone_number, code, client_role, status)
VALUES ('admin','admin@gmail.com', '$2a$10$4R2NCOV.sMJ9DDMOPY8FPO4boay4/VWI9Syd8YA/YY9Fjz0V7eg/u', 'ROLE_ADMIN', '8000', null, 'TEACHER', 'ACTIVE')
    on conflict do nothing;
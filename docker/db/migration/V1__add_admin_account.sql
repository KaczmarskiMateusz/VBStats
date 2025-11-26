DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@vbs.com') THEN
        INSERT INTO app.users (external_id, email, username, phone, active, confirmed, role, password)
        VALUES (
          '5ac873ad-4902-4115-a649-f4ed683af167',
          'admin@vbs.com',
          'admin',
          '123456789',
          true,
          true,
          'ADMIN',
          '$2a$12$8gyAm6OTWRQT8gPTGALHluq5lmR2Z3VZVoAiRytxzbo8inMqbZvYG'
        );
    END IF;
END $$;

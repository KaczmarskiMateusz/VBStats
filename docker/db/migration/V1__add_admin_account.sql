DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@vbs.com') THEN
        INSERT INTO app.users (external_id, email, username, phone, active, confirmed, role, password)
        VALUES (
          '00000000-0000-0000-0000-000000000001',
          'admin@vbs.com',
          'admin',
          '000000000',
          true,
          true,
          'ADMIN',
          '$2a$12$8gyAm6OTWRQT8gPTGALHluq5lmR2Z3VZVoAiRytxzbo8inMqbZvYG'
        );
    END IF;
END $$;

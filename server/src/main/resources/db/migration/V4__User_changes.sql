ALTER TABLE users ADD COLUMN created_date DATE NOT NULL DEFAULT NOW();
ALTER TABLE users ADD COLUMN description TEXT;
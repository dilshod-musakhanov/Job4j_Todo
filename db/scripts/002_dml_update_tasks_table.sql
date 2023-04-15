ALTER TABLE tasks ADD COLUMN title TEXT;
UPDATE tasks SET title = 'Default Title' WHERE title IS NULL;
ALTER TABLE tasks ALTER COLUMN title SET NOT NULL;
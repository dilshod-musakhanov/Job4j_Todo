ALTER TABLE tasks ADD COLUMN category_id int REFERENCES categories(id);
UPDATE tasks SET category_id = (SELECT id FROM categories WHERE name = 'Job');
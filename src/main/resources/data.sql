INSERT INTO users (name, email) VALUES ('Ana Souza', 'ana.souza@taskflow.com');
INSERT INTO users (name, email) VALUES ('Bruno Lima', 'bruno.lima@taskflow.com');

INSERT INTO projects (name, description, created_at, owner_id) VALUES ('Migração de API', 'Migrar endpoints legados para o novo padrão REST', NOW(), 1);
INSERT INTO projects (name, description, created_at, owner_id) VALUES ('App Mobile v2', 'Reescrita do app mobile em Kotlin', NOW(), 2);

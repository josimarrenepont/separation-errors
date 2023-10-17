import { Pool } from 'pg';

// Configuração da conexão ao PostgreSQL

const pool = new Pool({

user: 'postgres',

host: 'localhost',

database: 'springboot_separationerrors',

password: '07111519',

port: 5432, // Porta padrão do PostgreSQL

});

export default pool;
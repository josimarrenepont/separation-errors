/* eslint-disable no-undef */
import express, { json } from 'express';
import { Pool } from 'pg'; // Importe o módulo de banco de dados (neste exemplo, estamos usando o PostgreSQL)
import routes from './routes';

const app = express();

const PORT = process.env.PORT || 3000;

app.use(json());

// Conecte as rotas da sua aplicação
// Conecte as rotas da sua aplicação
app.use('/', routes);

// Inicie o servidor
app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});


// Configuração da conexão ao PostgreSQL
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'springboot_separationerrors',
  password: '07111519',
  port: 5432, // Porta padrão do PostgreSQL
});

// Rota para filtrar erros com base nas datas
app.get('/separations', async (req, res) => {
  try {
    const { startDate, endDate } = req.query;

    // Execute a consulta SQL no banco de dados para selecionar os erros dentro do intervalo de datas
    const result = await pool.query(
      'SELECT * FROM separations WHERE date >= $1 AND date <= $2',
      [startDate, endDate]
    );

    // Envie a resposta (response) com os dados filtrados
    res.status(200).json(result.rows);
  } catch (error) {
    console.error('Erro ao buscar erros:', error);
    res.status(500).json({ error: 'Erro ao buscar erros' });
  }
});

// Inicie o servidor
app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});

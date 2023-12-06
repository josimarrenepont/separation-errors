// server.js

import cors from 'cors';
import express from 'express';

const app = express();

// Configure o middleware cors para permitir solicitações de qualquer origem
app.use(cors());

// Outras configurações e rotas do seu servidor...

// eslint-disable-next-line no-undef
const port = process.env.PORT || 8080; // Use a porta definida pelo ambiente ou a porta 8080 por padrão
app.listen(port, () => {
  console.log(`Servidor ouvindo na porta ${port}`);
});

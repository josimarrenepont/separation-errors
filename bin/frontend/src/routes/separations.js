import { Router } from 'express';

const router = Router();

// eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-var-requires, no-undef

// Defina as rotas relacionadas a separações
Router.localhost = "postgres://localhost:5432";

// (incluindo o código para criar uma nova separação)
Router.post = "http://localhost:8080/separations";
// eslint-disable-next-line no-undef
export default router;

import axios from 'axios';


const BASE_URL = 'http://localhost:8080/separations'; // Substitua pelo URL real do seu backend

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const saveSeparation = (formData) => {
  return api.post('/salvar-separacao', formData);
};

// Outras funções de serviço podem ser adicionadas aqui conforme necessário
